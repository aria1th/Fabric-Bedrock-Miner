package yan.lx.bedrockminer.utils;

import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.MinecraftClient;
import java.util.Arrays;
import java.util.ArrayList;

import static net.minecraft.block.Block.sideCoversSmallSquare;

public class CheckingEnvironment {
    static final ArrayList<Direction> HORIZONTAL = new ArrayList<Direction>(Arrays.asList(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH));
    public static BlockPos findNearbyFlatBlockToPlaceRedstoneTorch(ClientWorld world, BlockPos blockPos) {
        for (Direction offset : HORIZONTAL) {
            if ((sideCoversSmallSquare(world, blockPos.offset(offset), Direction.UP) && (world.getBlockState(blockPos.offset(offset).up()).getMaterial().isReplaceable()) || world.getBlockState(blockPos.offset(offset).up()).isOf(Blocks.REDSTONE_TORCH))) {
                return blockPos.offset(offset);
            }
        }
        return null;
    }

    public static BlockPos findPossibleSlimeBlockPos(ClientWorld world, BlockPos blockPos) {
        for (Direction offset : HORIZONTAL) {
            if (isCollidingPlayerEntity(world, blockPos.offset(offset))){
                continue;
            }
            if (sideCoversSmallSquare(world, blockPos.offset(offset), Direction.UP) && (world.getBlockState(blockPos.offset(offset).up()).getMaterial().isReplaceable())) {
                return blockPos.offset(offset);
            }
        }
        return null;
    }

    public static boolean has2BlocksOfPlaceToPlacePiston(ClientWorld world, BlockPos blockPos) {
        if (isCollidingPlayerEntity(world, blockPos) || isCollidingPlayerEntity(world, blockPos.up())){
            return false;
        }
        if (world.getBlockState(blockPos.up()).getHardness(world, blockPos.up()) == 0) {
            BlockBreaker.breakBlock(world, blockPos.up());
        }
        return world.getBlockState(blockPos.up()).getMaterial().isReplaceable() && world.getBlockState(blockPos.up().up()).getMaterial().isReplaceable();
    }

    public static ArrayList<BlockPos> findNearbyRedstoneTorch(ClientWorld world, BlockPos pistonBlockPos) {
        ArrayList<BlockPos> list = new ArrayList<>();
        for (Direction offset : HORIZONTAL) {
            if (world.getBlockState(pistonBlockPos.offset(offset)).isOf(Blocks.REDSTONE_TORCH)) {
                list.add(pistonBlockPos.east());
            }
        }
        return list;
    }
    public static boolean isCollidingPlayerEntity(ClientWorld world, BlockPos blockPos){
        final MinecraftClient minecraftClient = MinecraftClient.getInstance();
        PlayerEntity player = minecraftClient.player;
        return player.collidesWithStateAtPos(blockPos, world.getBlockState(blockPos));
    }
}
