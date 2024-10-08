/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.remoting.transport.netty4;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.remoting.ChannelHandler;
import org.apache.dubbo.remoting.Codec2;
import org.apache.dubbo.remoting.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.AbstractByteBufAllocator;
import io.netty.buffer.ByteBuf;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

/**
 * {@link NettyCodecAdapter}
 */
class NettyCodecAdapterTest {

    @Test
    void test() {
        Codec2 codec2 = Mockito.mock(Codec2.class);
        URL url = Mockito.mock(URL.class);
        ChannelHandler handler = Mockito.mock(ChannelHandler.class);
        NettyCodecAdapter nettyCodecAdapter = new NettyCodecAdapter(codec2, url, handler);
        io.netty.channel.ChannelHandler decoder = nettyCodecAdapter.getDecoder();
        io.netty.channel.ChannelHandler encoder = nettyCodecAdapter.getEncoder();
        Assertions.assertTrue(decoder instanceof ByteToMessageDecoder);
        Assertions.assertTrue(encoder instanceof MessageToByteEncoder);
    }

    @Test
    void testDecodeException() throws IOException {
        Codec2 codec2 = Mockito.mock(Codec2.class);
        doThrow(new IOException("testDecodeIllegalPacket")).when(codec2).decode(any(), any());

        URL url = Mockito.mock(URL.class);
        doReturn("default").when(url).getParameter(eq(Constants.CODEC_KEY));

        ChannelHandler handler = Mockito.mock(ChannelHandler.class);
        NettyCodecAdapter nettyCodecAdapter = new NettyCodecAdapter(codec2, url, handler);
        io.netty.channel.ChannelHandler decoder = nettyCodecAdapter.getDecoder();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel();
        embeddedChannel.pipeline().addLast(decoder);

        // simulate illegal data packet
        ByteBuf input = AbstractByteBufAllocator.DEFAULT.buffer();
        input.writeBytes("testDecodeIllegalPacket".getBytes(StandardCharsets.UTF_8));

        DecoderException decoderException = Assertions.assertThrows(DecoderException.class, () -> {
            embeddedChannel.writeInbound(input);
        });
        Assertions.assertTrue(decoderException.getCause() instanceof IOException);

        Assertions.assertEquals(0, input.refCnt());
    }
}
