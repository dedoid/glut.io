package dedoid.glutio.common.core.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class GlutTeleporter extends Teleporter {

    private final WorldServer world;

    private int x;
    private int y;
    private int z;

    public GlutTeleporter(WorldServer world, int x, int y, int z) {
        super(world);
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Override
    public void placeInPortal(Entity entity, float yaw) {
        this.world.getBlockState(new BlockPos(this.x, this.y, this.z));

        entity.setPosition(this.x, this.y, this.z);
        entity.motionX = 0.0f;
        entity.motionY = 0.0f;
        entity.motionZ = 0.0f;
    }

}
