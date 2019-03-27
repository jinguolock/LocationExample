package com.jinju.location.udp;

import com.jinju.location.protocol.UpCommand;

public interface MyUdpListener {
	public void handle(UpCommand command);
}
