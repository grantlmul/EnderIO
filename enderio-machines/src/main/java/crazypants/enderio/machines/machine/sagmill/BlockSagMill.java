package crazypants.enderio.machines.machine.sagmill;

import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import crazypants.enderio.base.init.IModObject;
import crazypants.enderio.base.machine.base.block.AbstractMachineBlock;
import crazypants.enderio.base.machine.render.RenderMappers;
import crazypants.enderio.base.paint.IPaintable;
import crazypants.enderio.base.render.IBlockStateWrapper;
import crazypants.enderio.base.render.IRenderMapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSagMill<T extends TileSagMill> extends AbstractMachineBlock<T>
    implements IPaintable.ISolidBlockPaintableBlock, IPaintable.IWrenchHideablePaint {

  public static BlockSagMill<TileSagMill.Normal> create(@Nonnull IModObject modObject) {
    BlockSagMill<TileSagMill.Normal> res = new BlockSagMill<>(modObject, TileSagMill.Normal.class);
    res.init();
    return res;
  }

  public static BlockSagMill<TileSagMill.Simple> create_simple(@Nonnull IModObject modObject) {
    BlockSagMill<TileSagMill.Simple> res = new BlockSagMill<TileSagMill.Simple>(modObject, TileSagMill.Simple.class) {
      @Override
      @SideOnly(Side.CLIENT)
      public @Nonnull IRenderMapper.IItemRenderMapper getItemRenderMapper() {
        return RenderMappers.SIMPLE_BODY_MAPPER;
      }

      @Override
      @SideOnly(Side.CLIENT)
      public IRenderMapper.IBlockRenderMapper getBlockRenderMapper() {
        return RenderMappers.SIMPLE_BODY_MAPPER;
      }
    };
    res.init();
    return res;
  }

  private BlockSagMill(@Nonnull IModObject modObject, @Nullable Class<T> teClass) {
    super(modObject, teClass);
  }

  @Override
  public @Nullable Container getServerGuiElement(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nullable EnumFacing facing,
      int param1, @Nonnull TileSagMill te) {
    return ContainerSagMill.create(player.inventory, te);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public @Nullable GuiScreen getClientGuiElement(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nullable EnumFacing facing,
      int param1, @Nonnull TileSagMill te) {
    return new GuiSagMill(player.inventory, te);
  }

  @Override
  public void randomDisplayTick(@Nonnull IBlockState bs, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Random rand) {
    TileSagMill te = getTileEntity(world, pos);
    if (te != null && te.isActive()) {
      EnumFacing front = te.facing;

      for (int i = 0; i < 3; i++) {
        double px = pos.getX() + 0.5 + front.getFrontOffsetX() * 0.51;
        double pz = pos.getZ() + 0.5 + front.getFrontOffsetZ() * 0.51;
        double py = pos.getY() + world.rand.nextFloat() * 0.8f + 0.1f;
        double v = 0.05;
        double vx = 0;
        double vz = 0;

        if (front == EnumFacing.NORTH || front == EnumFacing.SOUTH) {
          px += world.rand.nextFloat() * 0.8 - 0.4;
          vz += front == EnumFacing.NORTH ? -v : v;
        } else {
          pz += world.rand.nextFloat() * 0.8 - 0.4;
          vx += front == EnumFacing.WEST ? -v : v;
        }

        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, vx, 0, vz);
      }
    }
  }

  @Override
  protected void setBlockStateWrapperCache(@Nonnull IBlockStateWrapper blockStateWrapper, @Nonnull IBlockAccess world, @Nonnull BlockPos pos,
      @Nonnull TileSagMill tileEntity) {
    blockStateWrapper.addCacheKey(tileEntity.getFacing()).addCacheKey(tileEntity.isActive());
  }

}
