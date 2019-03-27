package com.jinju.location.netty;

import io.netty.channel.ChannelHandlerContext;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class ClientCommandParseThread extends Thread{
	private Logger logger = LogManager.getLogger(ClientCommandParseThread.class.getName());
	
	private String msg;
	
	private DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	
	private ChannelHandlerContext ctx;
	
	public ClientCommandParseThread(ChannelHandlerContext ctx,String msg){
		this.msg=msg;
		this.ctx=ctx;
	}
	
	public void run(){
		logger.entry();
		try{
			if(msg.startsWith("i:")){
				String gateId=msg.substring(2);
				MainServer.getInstance().addClient(gateId, ctx);
			}if(msg.startsWith("{")){
				
			}if(msg.startsWith("nd")){
				String node_data=msg.substring(2);
				//MainServer.getInstance().addClient(gateId, ctx);
			}else{
				InetSocketAddress  sa=(InetSocketAddress)(ctx.channel().remoteAddress());
		        String ip=sa.getAddress().getHostAddress();
				int port=sa.getPort();
				String key=ip+":"+port;
				MainServer.getInstance().setIpCommandReturn(key, msg);
				
				if(msg.startsWith("{")){
					JSONObject jo=JSON.parseObject(msg);
					if("node_data".equals(jo.getString("Type"))){
						String[] sss=jo.getString("Content").split(",");
						byte[] bs=new byte[sss.length];
						for(int i=0;i<sss.length;i++){
							int b=Integer.parseInt(sss[i], 16);
							bs[i]=(byte)b;
						}
						
						DatagramSocket client = new DatagramSocket();
				        InetAddress addr = InetAddress.getByName("121.40.250.159");
				        DatagramPacket sendPacket = new DatagramPacket(bs ,bs.length , addr , 8893);
				        client.send(sendPacket);

				        
					}else if("node_event".equals(jo.getString("Type"))){
						
					}
				}
			}
		}catch(Exception ex){
			logger.catching(ex);  
		}
		logger.exit();
	}
}
