package dedoid.glutio.client.render;

import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderTileMolecularAssembler extends TileEntitySpecialRenderer<TileMolecularAssembler> {

    //TODO: fix tesr rendering black
    @Override
    public void renderTileEntityAt(TileMolecularAssembler tile, double x, double y, double z, float particleTicks, int destroyStage) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);

        if (!tile.getRecipe().isEmpty()) {
            GlStateManager.pushMatrix();

            double angle = (System.currentTimeMillis() / 10) % 360;
            double floatCurve = Math.abs(Math.sin(Math.toRadians(angle)));
            floatCurve *= 0.3;

            GlStateManager.translate(0.5F, 1.25F + floatCurve, 0.5F);
            GlStateManager.scale(0.3F, 0.3F, 0.3F);
            GlStateManager.rotate((float) angle, 0, 1.0F, 0);

            Minecraft.getMinecraft().getRenderItem().renderItem(tile.getRecipe(), ItemCameraTransforms.TransformType.NONE);

            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
    }
}
