/*
 * This file is part of Mixin, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.asm.mixin.injection.invoke.util;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes.InjectionNode;

import java.util.Arrays;

public class InvokeUtil {
    public static Type[] getOriginalArgs(InjectionNode node) {
        return Type.getArgumentTypes(((MethodInsnNode) node.getOriginalTarget()).desc);
    }

    public static Type[] getCurrentArgs(InjectionNode node) {
        MethodInsnNode methodNode = (MethodInsnNode) node.getCurrentTarget();
        Type[] currentArgs = Type.getArgumentTypes(methodNode.desc);
        if (node.isReplaced() && node.hasDecoration(RedirectInjector.Meta.KEY) && methodNode.getOpcode() != Opcodes.INVOKESTATIC) {
            // A non-static redirect handler method will have an extra arg at the start that we don't care about.
            return Arrays.copyOfRange(currentArgs, 1, currentArgs.length);
        }
        return currentArgs;
    }
}
