package tanks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Terrain
{
   private String type;
   private List<Tank> tanks;
   private int area;

    public Terrain(String type, int area) {
        this.type = type;
        this.area = area;
        this.tanks  =new ArrayList<>();
    }

    public String addTank(Tank tank)
    {
        Tank current = tanks.stream().filter(x -> x.getBrand().equals(tank.getBrand())).findFirst().orElse(null);
        if(current != null && current.getModel().equals(tank.getModel()))
        {
            return "Tank with this brand and model already exists!";
        }
        if(type.equals("Swamp") && tank.getWeight() > 14000)
        {
            return String.format("This %s is too heavy for this terrain!", tank.getBrand());
        }
        tanks.add(tank);
        return String.format("Tank %s %s added.", tank.getBrand(), tank.getModel());
    }

    public boolean removeTank(String brand, String model)
    {
        Tank tank = tanks.stream().filter(x -> x.getBrand().equals(brand) && x.getModel().equals(model)).findFirst().orElse(null);
        return tanks.remove(tank);
    }

    public String getTanksByBarrelCaliberMoreThan(int barrelCaliber)
    {
        List<Tank> currentList = tanks.stream().filter(x -> x.getBarrelCaliber() > barrelCaliber).toList();
        if(currentList.isEmpty())
        {
            return "There are no tanks with the specified caliber.";
        }
        List<String> brands = new ArrayList<>();
        for(Tank tank : currentList)
        {
            brands.add(tank.getBrand());
        }
        return String.format("Tanks with caliber more than %dmm: ", barrelCaliber) + String.join(", ", brands);
    }

    public Tank getTankByBrandAndModel(String brand, String model)
    {
        return tanks.stream().filter(x -> x.getBrand().equals(brand) && x.getModel().equals(model)).findFirst().orElse(null);
    }

    public String getTheMostArmoredTank()
    {
        Tank tank = tanks.stream().max(Comparator.comparing(Tank::getArmor)).orElse(null);

        assert tank != null;
        return String.format("%s %s is the most armored tank with %dmm. armor thickness.",
                tank.getBrand(), tank.getModel(), tank.getArmor());
    }

    public int getCount()
    {
        return tanks.size();
    }

    public String getStatistics()
    {
        if(tanks.isEmpty())
        {
            return String.format("There are no tanks in the %s.", type.toLowerCase());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Tanks located in the %s:%n", type.toLowerCase()));
        for(Tank tank : tanks)
        {
            sb.append(String.format("-- %s %s%n", tank.getBrand(), tank.getModel()));
        }
        return sb.toString().trim();
    }
}
