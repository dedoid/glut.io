package dedoid.glutio.common.core.util;

import com.mojang.authlib.GameProfile;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;
import java.util.UUID;

public class FakePlayerGlutIO extends FakePlayer {

    private static final UUID uuid = UUID.fromString("f0724a85-c988-36d3-88b6-db301a0d806d");
    private static final GameProfile profile = new GameProfile(uuid, "[GlutIO]");

    public FakePlayerGlutIO(World world, BlockPos pos) {
        super(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(world.provider.getDimension()), profile);

        posX = pos.getX() + 0.5;
        posY = pos.getY() + 0.5;
        posZ = pos.getZ() + 0.5;

        connection = new FakeNetHandlerPlayServer(this);
    }

    @Override
    protected void onNewPotionEffect(PotionEffect packet) {

    }

    @Override
    protected void onChangedPotionEffect(PotionEffect packet, boolean p_70695_2_) {

    }

    @Override
    protected void onFinishedPotionEffect(PotionEffect packet) {

    }

    @Override
    protected void playEquipSound(@Nullable ItemStack stack) {

    }
}
