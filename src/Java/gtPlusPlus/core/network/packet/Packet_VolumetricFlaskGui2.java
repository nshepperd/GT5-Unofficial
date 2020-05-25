package gtPlusPlus.core.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import gtPlusPlus.GTplusplus;
import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.core.network.handler.AbstractClientMessageHandler;
import gtPlusPlus.core.tileentities.general.TileEntityVolumetricFlaskSetter;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet_VolumetricFlaskGui2 extends AbstractClientMessageHandler<Packet_VolumetricFlaskGui2> implements AbstractPacket {

	private int x;
	private int y;
	private int z;
	private int flaskValue;

	public Packet_VolumetricFlaskGui2() {
		
	}

	public Packet_VolumetricFlaskGui2(TileEntityVolumetricFlaskSetter tile, int aCustomValue) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
		flaskValue = aCustomValue;
		Logger.INFO("Created Packet with values ("+x+", "+y+", "+z+" | "+flaskValue+")");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(flaskValue);
		Logger.INFO("Writing to byte buffer.");
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		flaskValue = buf.readInt();
		Logger.INFO("Reading from byte buffer.");
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	public int getCustomValue() {
		return flaskValue;
	}
	
	public void setCustomValue(int aVal) {
		this.flaskValue = aVal;
	}

	protected TileEntityVolumetricFlaskSetter getTileEntity(Packet_VolumetricFlaskGui2 message, MessageContext ctx) {
		Logger.INFO("Trying to get tile.");
		World worldObj = getWorld(ctx);
		if(worldObj == null) {
			Logger.INFO("Bad world object.");
			return null;
		}
		TileEntity te = worldObj.getTileEntity(message.getX(), message.getY(), message.getZ());
		if(te == null) {
			Logger.INFO("Bad Tile.");
			return null;
		}
		if(te instanceof TileEntityVolumetricFlaskSetter) {
			Logger.INFO("Found Tile.");
			return (TileEntityVolumetricFlaskSetter) te;
		}
		Logger.INFO("Error.");
		return null;
	}

	protected World getWorld(MessageContext ctx) {
		if(ctx.side == Side.SERVER) {
			return ctx.getServerHandler().playerEntity.worldObj;
		} else {
			return GTplusplus.proxy.getClientWorld();
		}
	}

	@Override
	public String getPacketName() {
		return "Packet_VoluemtricFlaskSetter_ToClient";
	}

	@Override
	public IMessage handleClientMessage(EntityPlayer player, Packet_VolumetricFlaskGui2 message, MessageContext ctx) {
		TileEntityVolumetricFlaskSetter te = getTileEntity(message, ctx);
		if(te != null) {
			Logger.INFO("Setting value on tile. "+message.getCustomValue());
			te.setCustomValue(message.getCustomValue());
		}
		return null;
	}

}
