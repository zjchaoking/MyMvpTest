package com.kaicom.api.bluetooth.weight;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 耀华XK3190-A27+E 
 * @author scj
 *
 */
public class ScalesXK3190A27PlusEParser extends ScalesK3190A7Parser {

	private final byte[] array = { 0x31, (byte) 0xFE, 0x5B };

	@Override
	public void sendCommandFor(OutputStream output) throws IOException {
		output.write(array);
		output.flush();
		sleep(20);
	}

}
