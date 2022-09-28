package com.kraftics.liberium.packet.convert;

import com.kraftics.liberium.packet.reflection.*;

public class ChatComponentConverter implements ObjectConverter<ChatComponent> {
    public static final Class<?> NMS = Reflection.getNMSClass("IChatBaseComponent");
    public static final Class<?> TEXT = Reflection.getNMSClass("ChatComponentText");
    public static final Class<?> CHAT_MODIFIER = Reflection.getNMSClass("ChatModifier");
    public static final Class<?> CHAT_HEX_COLOR = Reflection.getNMSClass("ChatHexColor");
    public static final Class<?> CHAT_CLICKABLE = Reflection.getNMSClass("ChatClickable");
    public static final Class<?> CHAT_HOVERABLE = Reflection.getNMSClass("ChatHoverable");

    // Constructors
    private static final ConstructorInvoker<?> textComponentConstructor = Reflection.getConstructor(TEXT, String.class);
    private static final ConstructorInvoker<?> chatModifierConstructor = Reflection.getConstructor(CHAT_MODIFIER, CHAT_HEX_COLOR, Boolean.class,
            Boolean.class, Boolean.class, Boolean.class, Boolean.class, CHAT_CLICKABLE, CHAT_HOVERABLE, String.class, ReflectionUtil.getMinecraftKeyClass());
    private static final ConstructorInvoker<?> chatHexColorConstructor = Reflection.getConstructor(CHAT_HEX_COLOR, int.class);

    // IChatBaseComponent
    private static final MethodInvoker<String> getText = Reflection.getMethod(NMS, "getText", String.class);
    private static final MethodInvoker<?> getModifier = Reflection.getMethod(NMS, "getChatModifier", CHAT_MODIFIER);

    // ChatModifier
    private static final FieldAccessor<?> color = Reflection.getField(CHAT_MODIFIER, CHAT_HEX_COLOR, 0);
    private static final FieldAccessor<Boolean> bold = Reflection.getField(CHAT_MODIFIER, Boolean.class, 0);
    private static final FieldAccessor<Boolean> italic = Reflection.getField(CHAT_MODIFIER, Boolean.class, 1);
    private static final FieldAccessor<Boolean> underlined = Reflection.getField(CHAT_MODIFIER, Boolean.class, 2);
    private static final FieldAccessor<Boolean> strikethrough = Reflection.getField(CHAT_MODIFIER, Boolean.class, 3);
    private static final FieldAccessor<Boolean> obfuscated = Reflection.getField(CHAT_MODIFIER, Boolean.class, 4);
    private static final FieldAccessor<String> insertion = Reflection.getField(CHAT_MODIFIER, String.class, 0);

    // ChatHexColor
    private static final FieldAccessor<Integer> rgb = Reflection.getField(CHAT_HEX_COLOR, Integer.class, 0);

    // ChatComponentText
    private static final FieldAccessor<?> modifierField = Reflection.getField(TEXT, CHAT_MODIFIER, 0);

    @Override
    public ChatComponent getSpecific(Object generic) {
        ChatComponent chatComponent = new ChatComponent(getText.invoke(generic));
        Object modifier = getModifier.invoke(generic);
        if (modifier != null) {
            Object hexColor = color.get(modifier);
            chatComponent.color = hexColor == null ? null : rgb.get(hexColor);
            chatComponent.bold = bold.get(modifier);
            chatComponent.italic = italic.get(modifier);
            chatComponent.underlined = underlined.get(modifier);
            chatComponent.strikethrough = strikethrough.get(modifier);
            chatComponent.obfuscated = obfuscated.get(modifier);
            chatComponent.insertion = insertion.get(modifier);
        }
        return chatComponent;
    }

    @Override
    public Object getGeneric(ChatComponent specific) {
        Object generic = textComponentConstructor.invoke(specific.text);
        Object hexColor = specific.color == null ? null : chatHexColorConstructor.invoke(specific.color);
        modifierField.set(generic, chatModifierConstructor.invoke(hexColor, specific.bold, specific.italic, specific.underlined,
                specific.strikethrough, specific.obfuscated, null, null, specific.insertion, null));
        return generic;
    }

    @Override
    public Class<?> getGenericType() {
        return NMS;
    }

    @Override
    public Class<ChatComponent> getSpecificType() {
        return ChatComponent.class;
    }
}
