package dedoid.glutio.client.render;

import dedoid.glutio.common.block.tile.TileMolecularAssembler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderTileMolecularAssembler extends TileEntitySpecialRenderer<TileMolecularAssembler> {

    //TODO: fix render break when more thn one tile placed
    @Override
    public void renderTileEntityAt(TileMolecularAssembler tile, double x, double y, double z, float particleTicks, int destroyStage) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.color(1F, 1F, 1F, 1F);
        GlStateManager.translate(x, y, z);

        if (!tile.getRecipe().isEmpty()) {
            //RenderHelper.enableStandardItemLighting();

            //GlStateManager.enableLighting();
            GlStateManager.pushMatrix();

            double updown = (System.currentTimeMillis() / 10) % 360;
            //updown = Math.abs(Math.sin(Math.toRadians(updown)));
            //updown *= 0.3;

            GlStateManager.translate(0.5F, 1.5F, 0.5F);
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.rotate((float) updown, 0, 1.0F, 0);

            Minecraft.getMinecraft().getRenderItem().renderItem(tile.getRecipe(), ItemCameraTransforms.TransformType.GROUND);

            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}
