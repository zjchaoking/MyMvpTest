package com.kaicom.api.bluetooth.weight;

import com.kaicom.api.bluetooth.ScalesType;

public final class WeightParserFactory {

	private WeightParserFactory() {
	}
	
	public static BluetoothWeightParser createWeightHandler(ScalesType type) {
		if (type == null)
			return null;
		
		switch (type) {
		case Scales_None:
			return null;
		case K3190A1:
		case TDI300:
		case TDI200A1:
			return new ScalesTDI300Parser();
		case K3190AX:
		case K3190A_Plus:
			return new ScalesK3190APlusParser();
		case K3190A7:
		case QuanHeng:
			return new ScalesK3190A7Parser();
		case K3190A12:
			return new ScalesK3190A12Parser();
		case XK3190A27PlusE:
			return new ScalesXK3190A27PlusEParser();
		case MINSIDA:
			return new ScalesMinSiDaParser();
		case XK3190A27E:
			return new ScalesXK3190A27EParser();
		default:
			break;
		}
		return null;
	}
	
	
}
