package com.github.quiltservertools.blockbotapi;

import com.github.quiltservertools.blockbotapi.sender.MessageSender;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.advancement.Advancement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public interface Bot {
    void onChatMessage(MessageSender sender, String message);
    void onPlayerConnect(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server);
    void onPlayerDisconnect(ServerPlayNetworkHandler handler, MinecraftServer server);
    void onPlayerDeath(ServerPlayerEntity player, Text message);
    void onAdvancementGrant(ServerPlayerEntity player, Advancement advancement);
    void onServerStart(MinecraftServer server);
    void onServerStop(MinecraftServer server);

    void sendDiscordMessage(String content, String channel);
    void onDiscordMessage(String content, String channel);
}