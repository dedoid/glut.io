package dedoid.glutio.client.core.handler;

import dedoid.glutio.client.render.IModelRegister;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.RegistryDelegate;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;

public class ModelHandler {

    private static final Map<RegistryDelegate<Block>, IStateMapper> customStateMappers = ReflectionHelper.getPrivateValue(ModelLoader.class, null, "customStateMappers");
    private static final DefaultStateMapper fallbackMapper = new DefaultStateMapper();

    public static void registerModels() {
        for (Block block : Block.REGISTRY) {
            if (block instanceof IModelRegister) {
                ((IModelRegister) block).registerModels();
            }
        }

        for (Item item : Item.REGISTRY) {
            if (item instanceof IModelRegister) {
                ((IModelRegister) item).registerModels();
            }
        }
    }

    public static void registerBlockToState(Block block, int meta, IBlockState state) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, getModelResourceLocationForState(state));
    }

    private static ModelResourceLocation getModelResourceLocationForState(IBlockState state) {
        return customStateMappers.getOrDefault(state.getBlock().delegate, fallbackMapper).putStateModelLocations(state.getBlock()).get(state);
    }
}
