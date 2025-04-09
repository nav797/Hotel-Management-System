package project;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;


	
	interface Observer {
	 void update(String itemName, int quantity, int threshold);
	}
	
	class InventoryNotifier {
	 private static InventoryNotifier instance;
	 private List<Observer> observers = new ArrayList<>();
	 
	 public static InventoryNotifier getInstance() {
	        if (instance == null) {
	            instance = new InventoryNotifier();
	        }
	        return instance;
	    }
	
	 void registerObserver(Observer o) {
	 observers.add(o);
	 }
	 void removeObserver(Observer o) {
	 observers.remove(o);
	 }
	 void notifyObservers(String itemName, int quantity, int threshold) {
	 for (Observer o : observers) {
	 o.update(itemName, quantity,threshold);
	 }
	 }
	 
	}
	
	class InvNotification implements Observer {
	 @Override
	 public void update(String itemName, int quantity, int threshold) {
		 JOptionPane.showMessageDialog(null,"Low Stock Alert: "+itemName+" is currently at "+ quantity + ". Please add to be atleast " + threshold);
	 }
	}

