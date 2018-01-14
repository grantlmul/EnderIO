package crazypants.enderio.powertools.machine.capbank.network;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.enderio.core.common.util.Util;

import crazypants.enderio.base.power.PowerHandlerUtil;
import crazypants.enderio.powertools.init.PowerToolObject;
import crazypants.enderio.powertools.machine.capbank.TileCapBank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryImpl implements IInventory {

  public static boolean isInventoryEmtpy(TileCapBank cap) {
    for (ItemStack st : cap.getInventory()) {
      if (st != null) {
        return false;
      }
    }
    return true;
  }

  public static boolean isInventoryEmtpy(ItemStack[] inv) {
    if (inv == null) {
      return true;
    }
    for (ItemStack st : inv) {
      if (st != null) {
        return false;
      }
    }
    return true;
  }

  private ItemStack[] inventory;

  private TileCapBank capBank;

  public InventoryImpl() {
  }

  public TileCapBank getCapBank() {
    return capBank;
  }

  public void setCapBank(TileCapBank cap) {
    capBank = cap;
    if (cap == null) {
      inventory = null;
      return;
    }
    inventory = cap.getInventory();
  }

  public boolean isEmtpy() {
    return isInventoryEmtpy(inventory);
  }

  public ItemStack[] getStacks() {
    return inventory;
  }

  @Override
  public @Nonnull ItemStack getStackInSlot(int slot) {
    if (inventory == null) {
      return null;
    }
    if (slot < 0 || slot >= inventory.length) {
      return null;
    }
    return inventory[slot];
  }

  @Override
  public @Nonnull ItemStack decrStackSize(int slot, int amount) {
    return Util.decrStackSize(this, slot, amount);
  }

  @Override
  public void setInventorySlotContents(int slot, @Nullable ItemStack itemstack) {
    if (inventory == null) {
      return;
    }
    if (slot < 0 || slot >= inventory.length) {
      return;
    }
    inventory[slot] = itemstack;
  }

  @Override
  public @Nonnull ItemStack removeStackFromSlot(int index) {
    if (inventory == null) {
      return null;
    }
    ItemStack res = getStackInSlot(index);
    setInventorySlotContents(index, null);
    return res;
  }

  @Override
  public void clear() {
    if (inventory == null) {
      return;
    }
    for (int i = 0; i < inventory.length; i++) {
      inventory[i] = null;
    }
  }

  @Override
  public int getSizeInventory() {
    return 4;
  }

  // --- constant values

  @Override
  public @Nonnull String getName() {
    return PowerToolObject.block_cap_bank.getBlockNN().getUnlocalizedName() + ".name";
  }

  @Override
  public boolean hasCustomName() {
    return false;
  }

  @Override
  public int getInventoryStackLimit() {
    return 1;
  }

  @Override
  public boolean isUsableByPlayer(@Nonnull EntityPlayer p_70300_1_) {
    return true;
  }

  @Override
  public boolean isItemValidForSlot(int slot, @Nonnull ItemStack itemstack) {
    if (itemstack.isEmpty()) {
      return false;
    }
    return PowerHandlerUtil.getCapability(itemstack, null) != null;
  }

  @Override
  public void openInventory(@Nonnull EntityPlayer e) {
  }

  @Override
  public void closeInventory(@Nonnull EntityPlayer e) {
  }

  @Override
  public void markDirty() {
    if (capBank != null) {
      capBank.markDirty();
    }
  }

  @Override
  public @Nonnull ITextComponent getDisplayName() {
    return hasCustomName() ? new TextComponentString(getName()) : new TextComponentTranslation(getName(), new Object[0]);
  }

  @Override
  public int getField(int id) {
    return 0;
  }

  @Override
  public void setField(int id, int value) {
  }

  @Override
  public int getFieldCount() {
    return 0;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

}
