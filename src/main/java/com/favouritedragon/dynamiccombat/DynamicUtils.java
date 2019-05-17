package com.favouritedragon.dynamiccombat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DynamicUtils {

	public static <K, V, L extends NBTBase, W extends NBTBase> NBTTagList mapToNBT(HashMap<K, V> map,
																				   Function<K, L> keyFunction, Function<V, W> valueFunction, String keyTagName, String valueTagName){

		NBTTagList tagList = new NBTTagList();

		for(Map.Entry<K, V> entry : map.entrySet()){
			NBTTagCompound mapping = new NBTTagCompound();
			mapping.setTag(keyTagName, keyFunction.apply(entry.getKey()));
			mapping.setTag(valueTagName, valueFunction.apply(entry.getValue()));
			tagList.appendTag(mapping);
		}

		return tagList;
	}
}


