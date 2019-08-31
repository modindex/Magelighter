package com.genreshinobi.magelighter.blocks;

import com.genreshinobi.magelighter.ModBlocks;
import com.genreshinobi.magelighter.tileEntities.ClericsOvenEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClericsOven extends AbstractFurnaceBlock {

    // TODO: Clerics Oven - Add Recipes
    // TODO: Clerics Oven - Limit the Input Slot - No Ore
    // TODO: Clerics Oven - Recipes - Add Recipe for Clay Jars
    // TODO: Clerics Oven - Recipes - Determine the Byproducts
    // TODO: Clerics Oven - Do a another pass on the Oven Model to make it a solid block again
    // TODO: Clerics Oven - By Product Slot - Only Accepts Jars
    // TODO: Clerics Oven - By Product Output - Only By Products

    public final VoxelShape SHAPE;

    public ClericsOven() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(3.5F)
                .lightValue(13));
        this.setRegistryName("clerics_oven");
        this.SHAPE = this.generateShape();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) { return new ClericsOvenEntity(); }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote){
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
            return true;
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    protected void interactWith(World worldIn, BlockPos pos, PlayerEntity player) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof FurnaceTileEntity) {
            player.openContainer((INamedContainerProvider)tileentity);
            player.addStat(Stats.INTERACT_WITH_FURNACE);
        }
    }

    private VoxelShape generateShape()
    {
        List<VoxelShape> shapes = new ArrayList<>();
        shapes.add(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D)); // BASE
        shapes.add(Block.makeCuboidShape(1.0D, 2.0D, 1.0D, 14.0D, 8.0D, 15.0D)); // OVENBACK
        shapes.add(Block.makeCuboidShape(0.0D, 0.8D, 0.0D, 16.0D, 10.0D, 16.0D)); // RIM
        shapes.add(Block.makeCuboidShape(1.0D, 2.0D, 1.0D, 15.0D, 12.0D, 15.0D)); // OVENTOP
        shapes.add(Block.makeCuboidShape(14.0D, 2.0D, 1.0D, 15.0D, 8.0D, 5.0D)); // FRONTLEFT
        shapes.add(Block.makeCuboidShape(14.0D, 2.0D, 11.0D, 15.0D, 8.0D, 15.0D)); // FRONTRIGHT
        shapes.add(Block.makeCuboidShape(14.0D, 7.0D, 5.0D, 15.0D, 8.0D, 11.0D)); // FRONTTOP
        shapes.add(Block.makeCuboidShape(14.0D, 2.0D, 5.0D, 15.0D, 3.0D, 11.0D)); // FRONTBOTTOM
        shapes.add(Block.makeCuboidShape(14.0D, 3.0D, 14.0D, 15.0D, 7.0D, 7.0D)); // GRILLLEFT
        shapes.add(Block.makeCuboidShape(14.0D, 3.0D, 9.0D, 15.0D, 7.0D, 10.0D)); // GRILLRIGHT

        VoxelShape result = VoxelShapes.empty();
        for(VoxelShape shape : shapes)
        {
            result = VoxelShapes.combine(result, shape, IBooleanFunction.OR);
        }
        return result.simplify();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(BlockStateProperties.FACING)));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.get(LIT)) {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY();
            double d2 = (double)pos.getZ() + 0.5D;
            if (rand.nextDouble() < 0.1D) {
                worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = stateIn.get(FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;
            double d5 = direction$axis == Direction.Axis.X ? (double)direction.getXOffset() * 0.52D : d4;
            double d6 = rand.nextDouble() * 6.0D / 16.0D;
            double d7 = direction$axis == Direction.Axis.Z ? (double)direction.getZOffset() * 0.52D : d4;
            worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
        }
    }

}
