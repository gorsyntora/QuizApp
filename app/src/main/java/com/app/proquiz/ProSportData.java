package com.app.proquiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ProSportData {

    public static final String NFL[] = {
            "Carolina Panthers", "Chicago Bears", "Cincinnati Bengals", "Cleveland Browns", "Dallas Cowboys",
            "Denver Broncos", "Detroit Lions", "Green Bay Packers", "Houston Texans", "Indianapolis Colts", "Jacksonville Jaguars",
            "Kansas City Chiefs", " Las Vegas Raiders", "Los Angeles Chargers", "Los Angeles Rams", "Miami Dolphins",
            "Minnesota Vikings", "New England Patriots", "New Orleans Saints", "New York Giants",
            "New York Jets", "Philadelphia Eagles", "Pittsburg Steelers",
            "San Francisco 49ers", "Seattle Seahawks", "Tampa Bay Buccaneers ", "Tennessee Titans ", " Washington Football Team  "
    };
    public static final String NHL[] = {"Montreal Canadiens", "Chicago Blackhawks", "Boston Bruins" ,
                       "Toronto Maple Leafs", "Vancouver Canucks", "Ottawa Senators", "Pittsburgh Penguins",
            "Calgary Flames", "New York Rangers", "Edmonton Oilers"};

    public static final String NBA[] =
            {"Atlanta Hawks", "Boston Celtics", "Brooklyn Nets ", "Charlotte Hornets", "Chicago Bulls ",
                    "Cleveland Cavaliers", "Dallas Mavericks", "Denver Nuggets", "Detroit Pistons ", "Golden State Warriors", " ", " ",
                    " Houston Rockets ", "  Indiana Pacers ", "Los Angeles Clippers ", "Los Angeles Lakers", " Memphis Grizzlies ",
                    "Miami Heat", "Milwaukee Bucks", "Minnesota Timberwolves", "New Orleans Pelicans ", " New York Knicks ",
                    "Oklahoma City Thunder", "Orlando Magic", "Philadelphia 76ers", "Phoenix Suns", " Portland Trail Blazers ",
                    "Sacramento Kings ", "     San Antonio Spurs", " Toronto Raptors", " Utah Jazz ", " Washington Wizards "};
    private final static String MLS[] = {"Atlanta United", "Austin FC", "Charlotte FC", "Chicago Fire FC", "FC Cincinnati"};

    public static ArrayList<String> getBtnNames() {
        ArrayList<String> list = new ArrayList<>();
        List<String> nfl = Arrays.asList(NFL);
        Collections.shuffle(nfl);
        List<String> nhl = Arrays.asList(NHL);
        Collections.shuffle(nhl);
        List<String> nba = Arrays.asList(NBA);
        Collections.shuffle(nba);
        List<String> mls = Arrays.asList(MLS);
        Collections.shuffle(mls);
        list.add(nfl.get(0));
        list.add(nhl.get(0));
        list.add(nba.get(0));
        list.add(mls.get(0));
        return list;

    }
}
