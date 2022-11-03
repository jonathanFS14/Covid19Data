package org.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Covid19Data {

    private String region;
    private String aldersgruppe;
    private int bekfræftedeTilfælde;
    private int døde;
    private int indlagtePåIntensivAfdeling;
    private int indlagte;
    private String dato;

    public Covid19Data(String region, String aldersgruppe, int bekfræftedeTilfælde, int døde, int indlagtePåIntensivAfdeling, int indlagte, String dato) {
        this.region = region;
        this.aldersgruppe = aldersgruppe;
        this.bekfræftedeTilfælde = bekfræftedeTilfælde;
        this.døde = døde;
        this.indlagtePåIntensivAfdeling = indlagtePåIntensivAfdeling;
        this.indlagte = indlagte;
        this.dato = dato;
    }

    public String getRegion() {
        return region;
    }

    public String getAldersgruppe() {
        return aldersgruppe;
    }

    @Override
    public String toString() {
        return "Covid19Data: " + region + " " + aldersgruppe + " " + bekfræftedeTilfælde + " " + døde + " " + indlagtePåIntensivAfdeling + " " + indlagte + " " + dato;
    }

    public static void main(String[] args) {

        ArrayList<Covid19Data> arrayList = new ArrayList<>();

        File f = new File("C:\\Users\\jonat\\OneDrive\\Skrivebord\\noegletal_pr_region_pr_aldersgruppe.csv");
        try {
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                // læs linje
                String linje = sc.nextLine();
                String[] attributter = linje.split(";");
                Covid19Data indlaestdata = new Covid19Data(
                        attributter[0],
                        attributter[1],
                        Integer.parseInt(attributter[2]),
                        Integer.parseInt(attributter[3]),
                        Integer.parseInt(attributter[4]),
                        Integer.parseInt(attributter[5]),
                        attributter[6]
                );
                arrayList.add(indlaestdata);
            }
            sc.close();

        } catch (IOException e) {
            System.out.println("Fejl i kommunikation med fil: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Fejl i filformat: " + e.getMessage());
        }

        System.out.println("tast 1 for at sortere efter region");
        System.out.println("tast 2 for at sortere efter aldersgruppe");
        System.out.println("tast 3 for at sortere efter region primært og aldersgruppe sekundært");
        System.out.println("tast 4 for at sortere efter aldersgruppe primært og region sekundært");


        Scanner sc = new Scanner(System.in);
        int brugerInput = sc.nextInt();
        switch (brugerInput) {
            case 1:
                Collections.sort(arrayList, new RegionComporator());
                for (Covid19Data c : arrayList)
                    System.out.println(c);
                break;
            case 2:
                Collections.sort(arrayList, new AldersGruppeComparator());
                for (Covid19Data c : arrayList)
                    System.out.println(c);
                break;
            case 3:
                Collections.sort(arrayList, new RegionComporator().thenComparing(new AldersGruppeComparator()));
                for (Covid19Data c : arrayList)
                    System.out.println(c);
                break;
            case 4:
                Collections.sort(arrayList, new AldersGruppeComparator().thenComparing(new RegionComporator()));
                for (Covid19Data c : arrayList)
                    System.out.println(c);
                break;
        }
    }
}