package com.genreshinobi.magelighter.blocks;

import com.genreshinobi.magelighter.tileEntities.ClericsOvenEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ClericsOven extends Block {

    public final VoxelShape SHAPE;

    public ClericsOven() {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(2.0f)
                .lightValue(14));
        setRegistryName("clerics_oven");
        this.SHAPE = this.generateShape();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockState = super.getStateForPlacement(context);
        if (blockState != null) {
            blockState = blockState.with(BlockStateProperties.FACING, context.getNearestLookingDirection());
        }
        return blockState;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ClericsOvenEntity();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
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
        return state.with(BlockStateProperties.FACING, rot.rotate(state.get(BlockStateProperties.FACING)));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
