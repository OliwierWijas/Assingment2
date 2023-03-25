package assignment2.server;

import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Broadcaster
{
  private final InetAddress group;
  private final int port;

  public Broadcaster(String groupAddress, int port) throws IOException
  {
    this.group = InetAddress.getByName(groupAddress);
    this.port = port;
  }

  public synchronized void broadcast(Object message) throws IOException
  {
    try (DatagramSocket socket = new DatagramSocket()) {
      byte[] content = SerializationUtils.serialize(message);
      DatagramPacket packet = new DatagramPacket(content, content.length, group, port);
      socket.send(packet);
    }
  }
}
