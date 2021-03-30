package com.kraftics.krafticslib.packet.convert;

import org.bukkit.Material;

public class BlockData {
    private Material material;
    private int data;

    public BlockData(Material material, int data) {
        this.material = material;
        this.data = data;
    }

    public BlockData(Material material) {
        this(material, 0);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
