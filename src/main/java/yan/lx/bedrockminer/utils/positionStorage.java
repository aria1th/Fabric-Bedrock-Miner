package yan.lx.bedrockminer.utils;

import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

public class positionStorage{
    private final static Map<Long, Boolean> positionMap = new LinkedHashMap<Long, Boolean> ();
    public static boolean hasPos(BlockPos pos)
    {
        Long asLong = pos.asLong();
        if(positionMap.containsKey(asLong)){
            return positionMap.get(asLong);
        }
        return false;
    }
    public static void registerPos(BlockPos pos, boolean val)
    {
        positionMap.put(pos.asLong(), (Boolean) val);
    }
    public static ArrayList<BlockPos> getFalseMarkedPos()
    {
        ArrayList<BlockPos> FalseMarkedList = new ArrayList<BlockPos>();
        for (Long position : positionMap.keySet()) {
            if (!positionMap.get(position)) {
                FalseMarkedList.add(BlockPos.fromLong(position));
            }
        }
            return FalseMarkedList;
    }
}