/*
   Copyright (C) 2020  Shawn Carter
   Contact: shawn.jf.carter@gmail.com
   
   This file is part of Himeji Map Viewer (HMV).

    HMV is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    HMV is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with HMV.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.takenouchitr.himeji.MCCompat;

import com.takenouchitr.himeji.EntityType;

public enum Entity 
{
	//Passive
	SKELETON_HORSE		(0, EntityType.PASSIVE, 0xFFFF00FF),
	ZOMBIE_HORSE		(1, EntityType.PASSIVE, 0xFFFF00FF),
	DONKEY				(2, EntityType.PASSIVE, 0xFFFF00FF),
	MULE				(3, EntityType.PASSIVE, 0xFFFF00FF),
	BAT					(4, EntityType.PASSIVE, 0xFFFF00FF),
	PIG					(5, EntityType.PASSIVE, 0xFFFF00FF),
	SHEEP				(6, EntityType.PASSIVE, 0xFFFF00FF),
	COW					(7, EntityType.PASSIVE, 0xFFFF00FF),
	CHICKEN				(8, EntityType.PASSIVE, 0xFFFF00FF),
	SQUID				(9, EntityType.PASSIVE, 0xFFFF00FF),
	WOLF				(10, EntityType.PASSIVE, 0xFFFF00FF),
	MOOSHROOM			(11, EntityType.PASSIVE, 0xFFFF00FF),
	SNOWMAN				(12, EntityType.PASSIVE, 0xFFFF00FF),
	OCELOT				(13, EntityType.PASSIVE, 0xFFFF00FF),
	IRON_GOLEM			(14, EntityType.PASSIVE, 0xFFFF00FF),
	HORSE				(15, EntityType.PASSIVE, 0xFFFF00FF),
	RABBIT				(16, EntityType.PASSIVE, 0xFFFF00FF),
	POLAR_BEAR			(17, EntityType.PASSIVE, 0xFFFF00FF),
	LLAMA				(18, EntityType.PASSIVE, 0xFFFF00FF),
	PARROT				(19, EntityType.PASSIVE, 0xFFFF00FF),
	VILLAGER			(20, EntityType.PASSIVE, 0xFFFF00FF),
	CAT					(83, EntityType.PASSIVE, 0xFFFF00FF),
	TRADER_LLAMA		(84, EntityType.PASSIVE, 0xFFFF00FF),
	SALMON				(85, EntityType.PASSIVE, 0xFFFF00FF),
	TROPICAL_FISH		(86, EntityType.PASSIVE, 0xFFFF00FF),
	//Hostile
	ELDER_GUARDIAN		(21, EntityType.HOSTILE, 0xFFFF0000),
	WITHER_SKELETON		(22, EntityType.HOSTILE, 0xFFFF0000),
	STRAY				(23, EntityType.HOSTILE, 0xFFFF0000),
	HUSK				(24, EntityType.HOSTILE, 0xFFFF0000),
	ZOMBIE_VILLAGER		(25, EntityType.HOSTILE, 0xFFFF0000),
	EVOCATION_ILLAGER	(26, EntityType.HOSTILE, 0xFFFF0000),
	VEX					(27, EntityType.HOSTILE, 0xFFFF0000),
	VINDICATION_ILLAGER	(28, EntityType.HOSTILE, 0xFFFF0000),
	ILLUSION_ILLAGER	(29, EntityType.HOSTILE, 0xFFFF0000),
	CREEPER				(30, EntityType.HOSTILE, 0xFFFF0000),
	SKELETON			(31, EntityType.HOSTILE, 0xFFFF0000),
	SPIDER				(32, EntityType.HOSTILE, 0xFFFF0000),
	GIANT				(33, EntityType.HOSTILE, 0xFFFF0000),
	ZOMBIE				(34, EntityType.HOSTILE, 0xFFFF0000),
	SLIME				(35, EntityType.HOSTILE, 0xFFFF0000),
	GHAST				(36, EntityType.HOSTILE, 0xFFFF0000),
	ZOMBIE_PIGMAN		(37, EntityType.HOSTILE, 0xFFFF0000),
	ENDER_MAN			(38, EntityType.HOSTILE, 0xFFFF0000),
	CAVE_SPIDER			(39, EntityType.HOSTILE, 0xFFFF0000),
	SILVERFISH			(40, EntityType.HOSTILE, 0xFFFF0000),
	BLAZE				(41, EntityType.HOSTILE, 0xFFFF0000),
	MAGMA_CUBE			(42, EntityType.HOSTILE, 0xFFFF0000),
	ENDER_DRAGON		(43, EntityType.HOSTILE, 0xFFFF0000),
	WITHER				(44, EntityType.HOSTILE, 0xFFFF0000),
	WITCH				(45, EntityType.HOSTILE, 0xFFFF0000),
	ENDERMITE			(46, EntityType.HOSTILE, 0xFFFF0000),
	GUARDIAN			(47, EntityType.HOSTILE, 0xFFFF0000),
	SHULKER				(48, EntityType.HOSTILE, 0xFFFF0000),
	//Drops
	ITEM				(49, EntityType.DROP, 0xFFFFFF00),
	XP_ORB				(50, EntityType.DROP, 0xFFFFFF00),
	//Immobile
	AREA_EFFECT_CLOUD	(51, EntityType.IMMOBILE, 0xFF00FFFF),
	LEASH_KNOT			(52, EntityType.IMMOBILE, 0xFF00FFFF),
	PAINTING			(53, EntityType.IMMOBILE, 0xFF00FFFF),
	ITEM_FRAME			(54, EntityType.IMMOBILE, 0xFF00FFFF),
	ARMOR_STAND			(55, EntityType.IMMOBILE, 0xFF00FFFF),
	EVOCATION_FANGS		(56, EntityType.IMMOBILE, 0xFF00FFFF),
	ENDER_CRYSTAL		(57, EntityType.IMMOBILE, 0xFF00FFFF),
	//Projectiles
	EGG					(58, EntityType.PROJECTILE, 0xFFFFFFFF),
	ARROW				(59, EntityType.PROJECTILE, 0xFFFFFFFF),
	SNOWBALL			(60, EntityType.PROJECTILE, 0xFFFFFFFF),
	FIREBALL			(61, EntityType.PROJECTILE, 0xFFFFFFFF),
	SMALL_FIREBALL		(62, EntityType.PROJECTILE, 0xFFFFFFFF),
	ENDER_PEARL			(63, EntityType.PROJECTILE, 0xFFFFFFFF),
	EYE_OF_ENDER_SIGNAL	(64, EntityType.PROJECTILE, 0xFFFFFFFF),
	POTION				(65, EntityType.PROJECTILE, 0xFFFFFFFF),
	XP_BOTTLE			(66, EntityType.PROJECTILE, 0xFFFFFFFF),
	WITHER_SKULL		(67, EntityType.PROJECTILE, 0xFFFFFFFF),
	FIREWORKD_ROCKET	(68, EntityType.PROJECTILE, 0xFFFFFFFF),
	SPECTRAL_ARROW		(69, EntityType.PROJECTILE, 0xFFFFFFFF),
	SHULKER_BULLET		(70, EntityType.PROJECTILE, 0xFFFFFFFF),
	DRAGON_FIREBALL		(71, EntityType.PROJECTILE, 0xFFFFFFFF),
	LLAMA_SPIT			(72, EntityType.PROJECTILE, 0xFFFFFFFF),
	//Blocks
	TNT					(73, EntityType.BLOCK, 0xFFFF0000),
	FALLING_BLOCK		(74, EntityType.BLOCK, 0xFFFFFF55),
	//Vehicles
	COMMANDBLOCK_MINECART	(75, EntityType.VEHICLE, 0xFF888888),
	BOAT					(76, EntityType.VEHICLE, 0xFF888888),
	MINECART				(77, EntityType.VEHICLE, 0xFF888888),
	CHEST_MINECART			(78, EntityType.VEHICLE, 0xFF888888),
	FURNACE_MINECART		(79, EntityType.VEHICLE, 0xFF888888),
	TNT_MINECART			(80, EntityType.VEHICLE, 0xFF888888),
	HOPPER_MINECART			(81, EntityType.VEHICLE, 0xFF888888),
	SPAWNER_MINECART		(82, EntityType.VEHICLE, 0xFF888888);
	
	public final int ID;
	public final EntityType TYPE;
	public final int COLOR;
	
	private Entity(int ID, EntityType TYPE, int COLOR)
	{
		this.ID = ID;
		this.TYPE = TYPE;
		this.COLOR = COLOR;
	}
}