package dedoid.glutio.common;

import com.mojang.authlib.GameProfile;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class FakePlayerGlutIO extends FakePlayer {

    public FakePlayerGlutIO(World world, BlockPos pos, GameProfile profile) {
        super(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(world.provider.getDimension()), profile);

        posX = pos.getX() + 0.5F;
        posY = pos.getY() + 0.5F;
        posZ = pos.getZ() + 0.5F;
    }
}
