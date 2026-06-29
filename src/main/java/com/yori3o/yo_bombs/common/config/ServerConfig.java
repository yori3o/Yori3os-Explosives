package com.yori3o.yo_bombs.common.config;


import net.minecraft.resources.Identifier;
import net.fabricmc.loader.api.FabricLoader;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.autogen.FloatSlider;
import dev.isxander.yacl3.config.v2.api.autogen.IntSlider;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;



public class ServerConfig {

    public static ConfigClassHandler<ServerConfig> HANDLER = ConfigClassHandler.createBuilder(ServerConfig.class)
            .id(Identifier.fromNamespaceAndPath("yo_bombs", "server_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("yo_bombs-server.json5"))
                    .setJson5(true)
                    .build())
            .build();
    
    
    @AutoGen(group = "weapon", category = "server")
    @FloatSlider(min = 0, max = 1, step = 0.01f)
    @SerialEntry
    public float defaultKnockbackMultiplier = 0.75f;

    @AutoGen(group = "weapon", category = "server")
    @FloatSlider(min = 0, max = 3, step = 0.01f)
    @SerialEntry
    public float grenadeDamageMultiplier = 0.6f;

    @AutoGen(group = "weapon", category = "server")
    @FloatSlider(min = 0, max = 3, step = 0.01f)
    @SerialEntry
    public float bombDamageMultiplier = 0.6f;

    @AutoGen(group = "weapon", category = "server")
    @FloatSlider(min = 0, max = 3, step = 0.01f)
    @SerialEntry
    public float dynamiteDamageMultiplier = 0.6f;


    @AutoGen(group = "time", category = "server")
    @IntSlider(min = 3, max = 7, step = 2)
    @SerialEntry
    public int grenadeDetonationTime = 3;

    @AutoGen(group = "time", category = "server")
    @IntSlider(min = 3, max = 7, step = 2)
    @SerialEntry
    public int bombDetonationTime = 5;

    @AutoGen(group = "time", category = "server")
    @IntSlider(min = 3, max = 7, step = 2)
    @SerialEntry
    public int dynamiteDetonationTime = 7;


    @AutoGen(group = "explosion", category = "server")
    @FloatSlider(min = 0, max = 10, step = 0.1f)
    @SerialEntry
    public float grenadeRadius = 2f;

    @AutoGen(group = "explosion", category = "server")
    @FloatSlider(min = 0, max = 10, step = 0.1f)
    @SerialEntry
    public float bombRadius = 3f;

    @AutoGen(group = "explosion", category = "server")
    @FloatSlider(min = 0, max = 10, step = 0.1f)
    @SerialEntry
    public float dynamiteRadius = 4.6f;

    @AutoGen(group = "explosion", category = "server")
    @Boolean
    @SerialEntry
    public boolean grenadeBreakBlocks = false;

    @AutoGen(group = "explosion", category = "server")
    @Boolean
    @SerialEntry
    public boolean bombBreakBlocks = true;

    @AutoGen(group = "explosion", category = "server")
    @Boolean
    @SerialEntry
    public boolean dynamiteBreakBlocks = true;

}