package hw1;

/**
 * A class that outputs the charge of a removealbe and rechargable battery
 * @author antmanphan
 * @version 1.0
 */

public class CameraBattery  
{
	/** The constant for the maximum battery charge. */
	public static final int NUM_CHARGER_SETTINGS = 4;
	/** The constant for the minimum battery charge. */
	public static final double CHARGE_RATE = 2.0;
	/** The constant for the default camera power consumption per minute. */
	public static final double DEFAULT_CAMERA_POWER_CONSUMPTION = 1;
	
	private double batteryStartingCharge; /* The current battery charge in mAh */
	private double batteryCapacity; /* The current capacity that the battery is holding */ 
	private double cameraPowerConsumption;/* The current camera power consumption per minute */
	private int chargerSetting; /* The current setting of the charger */
	private double batteryCharge; /* The battery's charge status */
	private double cameraCharge; /* The current charge of the camera*/
	private double totalDrain; /* The ending or total drain of the battery */
	private boolean connected; /* Determines if the battery is connected to the charger or not */
	
	/** Constructor that creats/initializes a new camera battery, which is disconnected from the camera and the external charge
	 *  @param batteryStartingCharge; current starting charge of the battery
	 *  @param batteryCapacity; the current capcity of the battery
	 */
	
	public CameraBattery (double batteryStartingCharge, double batteryCapacity) 
	{
		this.batteryCapacity = batteryCapacity;
		this.batteryStartingCharge = Math.min(batteryStartingCharge, batteryCapacity);
		this.batteryCharge = this.batteryStartingCharge;
		this.cameraPowerConsumption = DEFAULT_CAMERA_POWER_CONSUMPTION;
		this.chargerSetting = 0;
		this.totalDrain = 0;
		this.connected = false;
	}
	
	/* Detects if the button was pressed on the external charger */
	public void buttonPress() 
	{
		chargerSetting = (chargerSetting + 1) % NUM_CHARGER_SETTINGS;
	}
	
	/**
	 * Calculates the current charge of the camera and outputs the total charge
	 * @param minutes
	 * @return Returns the new and current charge of the battery
	 */
	
	public double cameraCharge (double minutes)
	{
		double charge = CHARGE_RATE * minutes;
		double newBatteryCharge = Math.min(batteryCharge + charge, batteryCapacity);
		double acutalCharge = newBatteryCharge - batteryCharge;
		batteryCharge = newBatteryCharge;
		return acutalCharge;
	}
	
	/**
	 * Calculates the total drain of the battery
	 * @param minutes
	 * @return Returns the calculated final drain of the battery
	 */
	
	public double drain (double minutes)
	{
		double drain = cameraPowerConsumption * minutes;
		double newBatteryCharge = Math.max(batteryCharge - drain, 0);
		double actualDrain = batteryCharge - newBatteryCharge;
		batteryCharge = newBatteryCharge;
		totalDrain += actualDrain;
		return actualDrain;
	}
	
	/**
	 * Calculates and returns the total charge of the battery
	 * @param minutes
	 * @return Returns the current and total charge of the external battery
	 */
	
	public double externalCharge (double minutes)
	{
		double charge = CHARGE_RATE * chargerSetting * minutes;
		double newBatteryCharge = Math.min(batteryCharge + charge, batteryCapacity);
		double actualCharge = newBatteryCharge - batteryCharge;
		batteryCharge = newBatteryCharge;
		return actualCharge;
	}
	
	
	/* Resets the battery back to 0 */
	public void resetBatteryMonitor()
	{
		totalDrain = 0;
	}
	
	/* Returns the current battery capacity */
	public double getBatteryCapacity()
	{
		return batteryCapacity;
	}
	
	/* Returns the current charge of the battery */ 
	public double getBatteryCharge()
	{
		return batteryCharge;
	}
	
	/* Returns the calculated charge of the camera's battery */ 
	public double getCameraCharge()
	{
		return cameraCharge;
	}
	
	/* Returns the current camera power consumption */
	public double getCameraPowerConsumption()
	{
		return cameraPowerConsumption;
	}
	
	/* Returns the status of the external charger */
	public int getChargerSetting()
	{
		return chargerSetting;
	}
	
	/* Returns the total drain of the battery */
	public double getTotalDrain()
	{
		return totalDrain;
	}
	
	/* Determines if the battery is connected to the exeternal charger */
	public void moveBatteryExternal()
	{
		connected = false;
	}
	
	/* Determines if the battery is in the camera */
	public void moveBatteryCamera()
	{
		connected = true;
	}
	
	/* Removes the battery from the camera */
	public void removeBattery()
	{
		connected = false;
	}
	
	/* Sets the power consumption of the camera */
	public void setCameraPowerConsumption(double cameraPowerConsumption)
	{
		this.cameraPowerConsumption = cameraPowerConsumption;
	}
}
