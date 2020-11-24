package adapter;

import adapter.adaptee.ZGSocketImpl;
import adapter.adapter.ZGSocketGuojiSocketAdapter;

public class DesignPatternDemo {

	public static void main(String[] args) {
		DeGuoHotel hotel = new DeGuoHotel();
		// hotel.setDBSocket(new DBSocketGuojiSocketAdapter(new DBSocketImpl()));
		hotel.setGuoJiSocket(new ZGSocketGuojiSocketAdapter(new ZGSocketImpl()));
		hotel.charge();
	}

}
