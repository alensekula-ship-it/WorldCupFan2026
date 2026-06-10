
package com.alensgarage.worldcupfan2026;

import android.app.*;
import android.os.*;
import android.content.*;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.text.InputType;
import java.text.*;
import java.util.*;

public class MainActivity extends Activity {
    final int RED = Color.rgb(223, 0, 52);
    final int RED2 = Color.rgb(145, 0, 30);
    final int BLUE = Color.rgb(20, 88, 180);
    final int GREEN = Color.rgb(0, 160, 90);
    final int GOLD = Color.rgb(246, 190, 40);
    final int ORANGE = Color.rgb(255, 120, 45);

    int bg, card, text, muted, line, nav, chip;
    boolean dark;
    String myTeam = "Croatia", active = "Home";
    LinearLayout root, content, bottom;
    TextView title, subtitle;
    Data data;
    SharedPreferences prefs;
    Handler handler = new Handler();

    public void onCreate(Bundle b) {
        super.onCreate(b);
        prefs = getSharedPreferences("wcf2026", 0);
        dark = prefs.getBoolean("dark", false);
        myTeam = prefs.getString("team", "Croatia");
        data = new Data(this);
        colors();
        build();
        showHome();
    }

    void colors() {
        bg = dark ? Color.rgb(7, 10, 18) : Color.rgb(245, 247, 251);
        card = dark ? Color.rgb(23, 27, 39) : Color.WHITE;
        text = dark ? Color.WHITE : Color.rgb(22, 24, 32);
        muted = dark ? Color.rgb(188, 193, 207) : Color.rgb(91, 96, 110);
        line = dark ? Color.rgb(55, 61, 78) : Color.rgb(226, 230, 238);
        nav = dark ? Color.rgb(15, 18, 28) : Color.WHITE;
        chip = dark ? Color.rgb(34, 40, 58) : Color.rgb(236, 239, 246);
    }

    void build() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);
        setContentView(root);

        LinearLayout head = new LinearLayout(this);
        head.setOrientation(LinearLayout.VERTICAL);
        head.setPadding(dp(20), dp(22), dp(20), dp(16));
        head.setBackground(grad(RED2, RED, 0));
        root.addView(head, new LinearLayout.LayoutParams(-1, dp(146)));

        title = label("World Cup Fan 2026", 29, Color.WHITE, true);
        subtitle = label("v6.0 Pro Tournament Edition • Sellable build", 14, Color.WHITE, false);
        head.addView(title);
        head.addView(subtitle);

        ScrollView sv = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(12), dp(12), dp(12), dp(96));
        sv.addView(content);
        root.addView(sv, new LinearLayout.LayoutParams(-1, 0, 1));

        bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setGravity(Gravity.CENTER);
        bottom.setPadding(dp(8), dp(7), dp(8), dp(7));
        bottom.setBackgroundColor(nav);
        root.addView(bottom, new LinearLayout.LayoutParams(-1, dp(78)));

        navItem("🏠", "Home");
        navItem("⚽", "Matches");
        navItem("🏆", "Groups");
        navItem("🔮", "Predict");
        navItem("☰", "More");
    }

    void rebuildCurrent() {
        colors();
        build();
        if (active.equals("Home")) showHome();
        else if (active.equals("Matches")) showMatches("", "All");
        else if (active.equals("Groups")) showGroups();
        else if (active.equals("Predict")) showPredictor();
        else showMore();
    }

    void navItem(String icon, String name) {
        TextView v = label(icon + "\n" + name, 11, text, true);
        v.setGravity(Gravity.CENTER);
        v.setBackground(round(active.equals(name) ? (dark ? Color.rgb(48, 55, 76) : Color.rgb(228, 233, 246)) : chip, dp(18), 0, 0));
        v.setOnClickListener(x -> {
            if (name.equals("Home")) showHome();
            else if (name.equals("Matches")) showMatches("", "All");
            else if (name.equals("Groups")) showGroups();
            else if (name.equals("Predict")) showPredictor();
            else showMore();
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -1, 1);
        lp.setMargins(dp(4), 0, dp(4), 0);
        bottom.addView(v, lp);
    }

    void setHeader(String h, String s, String tab) {
        active = tab;
        title.setText(h);
        subtitle.setText(s);
        content.removeAllViews();
    }

    TextView label(String s, int sp, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(sp);
        t.setTextColor(color);
        t.setPadding(dp(4), dp(3), dp(4), dp(3));
        if (bold) t.setTypeface(Typeface.DEFAULT_BOLD);
        return t;
    }

    TextView pill(String s, int bgc, int fg) {
        TextView t = label(s, 14, fg, true);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(14), dp(10), dp(14), dp(10));
        t.setBackground(round(bgc, dp(18), 0, 0));
        return t;
    }

    TextView cta(String s) {
        TextView b = label(s, 15, Color.WHITE, true);
        b.setGravity(Gravity.CENTER);
        b.setPadding(dp(14), dp(14), dp(14), dp(14));
        b.setBackground(grad(RED2, RED, dp(20)));
        return b;
    }

    LinearLayout card() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(17), dp(17), dp(17), dp(17));
        l.setBackground(round(card, dp(24), line, 2));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 0, 0, dp(13));
        content.addView(l, lp);
        return l;
    }

    LinearLayout miniCard(LinearLayout parent) {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(13), dp(13), dp(13), dp(13));
        l.setBackground(round(dark ? Color.rgb(31, 36, 52) : Color.rgb(247, 249, 253), dp(18), line, 1));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -2, 1);
        lp.setMargins(dp(4), dp(4), dp(4), dp(4));
        parent.addView(l, lp);
        return l;
    }

    LinearLayout row() {
        LinearLayout r = new LinearLayout(this);
        r.setOrientation(LinearLayout.HORIZONTAL);
        r.setGravity(Gravity.CENTER_VERTICAL);
        return r;
    }

    void addButton(LinearLayout parent, String text, View.OnClickListener l) {
        TextView b = cta(text);
        b.setOnClickListener(l);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, dp(7), 0, 0);
        parent.addView(b, lp);
    }

    void showHome() {
        setHeader("World Cup Fan 2026", "Premium offline predictor for serious football fans", "Home");
        bottom.removeAllViews(); navItem("🏠","Home"); navItem("⚽","Matches"); navItem("🏆","Groups"); navItem("🔮","Predict"); navItem("☰","More");

        LinearLayout hero = card();
        hero.addView(label("🔥 Pro Tournament Edition v6.0", 22, RED, true));
        hero.addView(label("A complete offline World Cup simulator: groups, match scores, qualified teams, bracket, dream final, statistics and shareable fan predictions.", 15, muted, false));
        LinearLayout stats = row();
        mini(stats, "72", "Group matches");
        mini(stats, "32", "Qualified teams");
        mini(stats, pctCompleted()+"%", "Prediction done");
        hero.addView(stats);
        addButton(hero, "Start Full Prediction", v -> showPredictor());

        LinearLayout count = card();
        count.addView(label("⚽ Kick-off countdown", 21, text, true));
        TextView days = label("", 35, RED, true);
        count.addView(days);
        Runnable r = new Runnable() {
            public void run() {
                String d = daysTo("2026-06-11 21:00");
                days.setText(d + " " + (d.equals("1") ? "day" : "days") + " to opening");
                handler.postDelayed(this, 60000);
            }
        };
        r.run();

        LinearLayout team = card();
        team.addView(label(flag(myTeam) + " My Team Command Center", 22, text, true));
        team.addView(label(nextTeamMatch(myTeam), 15, muted, false));
        addButton(team, "Open " + myTeam + " Hub", v -> showMyTeam());

        LinearLayout dash = card();
        dash.addView(label("📊 Prediction Dashboard", 22, text, true));
        LinearLayout drow = row();
        mini(drow, ""+data.playedCount(), "played");
        mini(drow, ""+data.goalCount(), "goals");
        mini(drow, data.bestAttack(), "best attack");
        dash.addView(drow);
        addButton(dash, "Share My Prediction", v -> sharePrediction());

        LinearLayout city = card();
        city.addView(label("🌎 Host Cities Spotlight", 22, text, true));
        city.addView(label("Mexico City • Toronto • Los Angeles • New York/New Jersey • Dallas • Miami • Vancouver • Atlanta • Houston • Kansas City • Boston • Seattle", 15, muted, false));
        addButton(city, "Open Host Cities", v -> showCities());
    }

    void mini(LinearLayout parent, String big, String small) {
        LinearLayout m = miniCard(parent);
        TextView b = label(big, 21, RED, true);
        b.setGravity(Gravity.CENTER);
        TextView s = label(small, 11, muted, false);
        s.setGravity(Gravity.CENTER);
        m.addView(b); m.addView(s);
    }

    String daysTo(String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(date);
            long diff = d.getTime() - System.currentTimeMillis();
            return String.valueOf(Math.max(0, diff / 86400000));
        } catch(Exception e) { return "0"; }
    }

    int pctCompleted() {
        return (int)Math.round(data.playedCount() * 100.0 / Math.max(1, data.matches.size()));
    }

    void showMatches(String filter, String group) {
        setHeader("All Matches", "Search, filter by group and enter your scores", "Matches");
        bottom.removeAllViews(); navItem("🏠","Home"); navItem("⚽","Matches"); navItem("🏆","Groups"); navItem("🔮","Predict"); navItem("☰","More");

        LinearLayout top = card();
        top.addView(label("🔎 Smart Match Search", 20, text, true));
        LinearLayout sr = row();
        EditText q = editText(filter, "Team, city or group...");
        TextView search = cta("Search");
        search.setOnClickListener(v -> showMatches(q.getText().toString(), group));
        sr.addView(q, new LinearLayout.LayoutParams(0, dp(58), 1));
        sr.addView(search, new LinearLayout.LayoutParams(dp(112), dp(58)));
        top.addView(sr);

        HorizontalScrollView hsv = new HorizontalScrollView(this);
        LinearLayout chips = new LinearLayout(this);
        chips.setOrientation(LinearLayout.HORIZONTAL);
        hsv.addView(chips);
        String[] gs = {"All","A","B","C","D","E","F","G","H","I","J","K","L","My Team"};
        for (String g : gs) {
            TextView ch = pill(g, g.equals(group) ? RED : chip, g.equals(group) ? Color.WHITE : text);
            ch.setOnClickListener(v -> showMatches(q.getText().toString(), ((TextView)v).getText().toString()));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-2, -2);
            lp.setMargins(0, dp(8), dp(8), 0);
            chips.addView(ch, lp);
        }
        top.addView(hsv);

        String f = filter == null ? "" : filter.toLowerCase(Locale.US).trim();
        int shown = 0;
        for (int i=0;i<data.matches.size();i++) {
            Match m = data.matches.get(i);
            if (group != null && !group.equals("All")) {
                if (group.equals("My Team")) {
                    if (!m.home.equals(myTeam) && !m.away.equals(myTeam)) continue;
                } else if (!m.group.equals(group)) continue;
            }
            String hay = (m.home+" "+m.away+" "+m.group+" "+m.venue+" "+m.date).toLowerCase(Locale.US);
            if (f.length()>0 && !hay.contains(f)) continue;
            shown++;
            LinearLayout c = card();
            TextView mt = label(flag(m.home)+" "+m.home+"  "+scoreText(m.homeGoals)+" : "+scoreText(m.awayGoals)+"  "+m.away+" "+flag(m.away), 17, text, true);
            c.addView(mt);
            c.addView(label(m.date+" • "+m.venue+" • Group "+m.group, 13, muted, false));
            if (m.homeGoals>=0 && m.awayGoals>=0) c.addView(label("✅ Played in your prediction", 13, GREEN, true));
            LinearLayout sc = row();
            EditText h = scoreBox(m.homeGoals);
            EditText a = scoreBox(m.awayGoals);
            TextView save = cta("Save");
            save.setOnClickListener(v -> {
                m.homeGoals = getInt(h);
                m.awayGoals = getInt(a);
                data.save();
                hideKeyboard(h);
                toast("Score saved");
                showMatches(q.getText().toString(), group);
            });
            sc.addView(h, new LinearLayout.LayoutParams(0, dp(54), 1));
            sc.addView(a, new LinearLayout.LayoutParams(0, dp(54), 1));
            sc.addView(save, new LinearLayout.LayoutParams(dp(92), dp(54)));
            c.addView(sc);
        }
        if (shown == 0) {
            LinearLayout n = card();
            n.addView(label("No matches found", 22, text, true));
            n.addView(label("Try another team, group, date or city.", 15, muted, false));
        }
    }

    String scoreText(int n){ return n>=0 ? ""+n : "-"; }

    EditText editText(String value, String hint) {
        EditText e = new EditText(this);
        e.setText(value == null ? "" : value);
        e.setHint(hint);
        e.setSingleLine(true);
        e.setTextColor(text);
        e.setHintTextColor(muted);
        e.setTextSize(16);
        e.setBackground(round(dark ? Color.rgb(31,36,52) : Color.rgb(250,251,254), dp(16), line, 1));
        e.setPadding(dp(12),0,dp(12),0);
        return e;
    }

    EditText scoreBox(int value) {
        EditText e = editText(value>=0 ? ""+value : "", "-");
        e.setInputType(InputType.TYPE_CLASS_NUMBER);
        e.setGravity(Gravity.CENTER);
        e.setTextSize(20);
        return e;
    }

    int getInt(EditText e) {
        try { return Integer.parseInt(e.getText().toString()); }
        catch(Exception ex) { return -1; }
    }

    void showGroups() {
        setHeader("Groups", "Full table with P W D L GF GA GD Pts", "Groups");
        bottom.removeAllViews(); navItem("🏠","Home"); navItem("⚽","Matches"); navItem("🏆","Groups"); navItem("🔮","Predict"); navItem("☰","More");

        Map<String,List<TeamRow>> map = data.standings();
        for (String g : data.groups) {
            LinearLayout c = card();
            c.addView(label("Group "+g, 23, RED, true));
            TextView header = label("Team              P  W  D  L  GF GA GD Pts", 12, muted, true);
            c.addView(header);
            for (int i=0;i<map.get(g).size();i++) {
                TeamRow r = map.get(g).get(i);
                String badge = i==0 ? "🥇" : (i==1 ? "🥈" : (i==2 ? "🥉" : "•"));
                int gd = r.gf-r.ga;
                String line = String.format(Locale.US, "%s %s %s   %d  %d  %d  %d   %d  %d  %+d   %d",
                    badge, flag(r.team), shortName(r.team), r.p, r.w, r.d, r.l, r.gf, r.ga, gd, r.pts);
                c.addView(label(line, 14, text, false));
            }
        }

        LinearLayout third = card();
        third.addView(label("🥉 Best third-placed teams", 22, text, true));
        List<TeamRow> thirds = data.bestThirds();
        for (int i=0;i<thirds.size();i++) {
            TeamRow r=thirds.get(i);
            third.addView(label((i+1)+". "+flag(r.team)+" "+r.team+" • Group "+r.group+" • "+r.pts+" pts", 14, i<8?GREEN:muted, true));
        }
    }

    String shortName(String s) {
        if (s.length() <= 13) return pad(s, 13);
        return s.substring(0, 12) + ".";
    }

    String pad(String s, int n) {
        StringBuilder b = new StringBuilder(s);
        while (b.length()<n) b.append(" ");
        return b.toString();
    }

    void showPredictor() {
        setHeader("Predictor", "Tournament path, bracket and sharing studio", "Predict");
        bottom.removeAllViews(); navItem("🏠","Home"); navItem("⚽","Matches"); navItem("🏆","Groups"); navItem("🔮","Predict"); navItem("☰","More");

        LinearLayout hero = card();
        hero.addView(label("🔮 Prediction Studio", 25, text, true));
        hero.addView(label("Complete your scores, generate qualified teams, build the bracket and share your World Cup story.", 15, muted, false));
        LinearLayout p = row();
        mini(p, pctCompleted()+"%", "completed");
        mini(p, ""+data.qualified().size(), "qualified");
        mini(p, data.championGuess(), "champion pick");
        hero.addView(p);
        addButton(hero, "Enter Match Scores", v -> showMatches("", "All"));
        addButton(hero, "Open Pro Bracket", v -> showBracket());
        addButton(hero, "Share Prediction", v -> sharePrediction());
        addButton(hero, "Dream Final Builder", v -> showDreamFinal());

        LinearLayout q = card();
        q.addView(label("✅ Qualified Teams Preview", 22, text, true));
        List<TeamRow> qual = data.qualified();
        for (int i=0;i<Math.min(16, qual.size());i++) {
            TeamRow r=qual.get(i);
            q.addView(label((i+1)+". "+flag(r.team)+" "+r.team+" • Group "+r.group+" • "+r.pts+" pts", 14, muted, false));
        }
        if (qual.size()>16) q.addView(label("+ "+(qual.size()-16)+" more teams in bracket", 14, RED, true));
    }

    void showBracket() {
        setHeader("Pro Bracket", "Round of 32 → Final", "Predict");
        content.removeAllViews();

        List<TeamRow> q = data.qualified();
        String[] rounds = {"Round of 32","Round of 16","Quarter-finals","Semi-finals","Final","Champion"};
        int games = 16;
        ArrayList<String> current = new ArrayList<>();
        for (TeamRow r : q) current.add(r.team);
        while (current.size()<32) current.add("TBD");

        for (String roundName : rounds) {
            LinearLayout c = card();
            c.addView(label("🏆 "+roundName, 23, RED, true));
            if (roundName.equals("Champion")) {
                String champ = current.size()>0 ? current.get(0) : "TBD";
                c.addView(label("👑 "+flag(champ)+" "+champ, 28, GOLD, true));
                addButton(c, "Share Champion Pick", v -> shareText("My World Cup 2026 champion: "+flag(champ)+" "+champ+" 👑\nMade with World Cup Fan 2026"));
                break;
            }
            ArrayList<String> next = new ArrayList<>();
            for (int i=0;i<games;i++) {
                String a = current.get((i*2) % current.size());
                String b = current.get((i*2+1) % current.size());
                String win = chooseWinner(a,b);
                next.add(win);
                LinearLayout match = new LinearLayout(this);
                match.setOrientation(LinearLayout.VERTICAL);
                match.setPadding(dp(12), dp(10), dp(12), dp(10));
                match.setBackground(round(dark?Color.rgb(31,36,52):Color.rgb(247,249,253), dp(16), line, 1));
                LinearLayout.LayoutParams mlp = new LinearLayout.LayoutParams(-1, -2);
                mlp.setMargins(0, dp(5), 0, dp(5));
                c.addView(match, mlp);
                match.addView(label(flag(a)+" "+a, 15, a.equals(win)?GREEN:text, true));
                match.addView(label("      vs", 12, muted, false));
                match.addView(label(flag(b)+" "+b, 15, b.equals(win)?GREEN:text, true));
                match.addView(label("Winner prediction: "+flag(win)+" "+win, 12, RED, true));
            }
            current = next;
            games = Math.max(1, games/2);
        }
    }

    String chooseWinner(String a, String b) {
        if (a.equals("TBD")) return b;
        if (b.equals("TBD")) return a;
        if (a.equals(myTeam)) return a;
        if (b.equals(myTeam)) return b;
        String[] elite = {"Brazil","France","Argentina","Spain","Germany","England","Portugal","Netherlands","Croatia"};
        for(String e: elite){ if(a.equals(e)) return a; if(b.equals(e)) return b; }
        return a;
    }

    void showDreamFinal() {
        setHeader("Dream Final", "Create a viral final prediction", "Predict");
        content.removeAllViews();
        LinearLayout c = card();
        c.addView(label("🏆 Dream Final Builder", 26, text, true));
        c.addView(label("Pick two finalists and share a clean prediction with friends.", 15, muted, false));
        Spinner a = spinner(data.teams, myTeam);
        Spinner b = spinner(data.teams, "Brazil");
        c.addView(label("Finalist 1", 13, muted, true));
        c.addView(a);
        c.addView(label("Finalist 2", 13, muted, true));
        c.addView(b);
        addButton(c, "Share Dream Final", v -> {
            String aa=a.getSelectedItem().toString(), bb=b.getSelectedItem().toString();
            shareText("🏆 My dream World Cup 2026 final:\n\n"+flag(aa)+" "+aa+" vs "+flag(bb)+" "+bb+"\n\nMade with World Cup Fan 2026");
        });
    }

    Spinner spinner(ArrayList<String> items, String selected) {
        Spinner sp = new Spinner(this);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sp.setAdapter(ad);
        int pos = items.indexOf(selected);
        if (pos>=0) sp.setSelection(pos);
        return sp;
    }

    void showMyTeam() {
        setHeader("My Team", "Personalized fan hub", "More");
        content.removeAllViews();
        LinearLayout c = card();
        c.addView(label(flag(myTeam)+" "+myTeam+" Hub", 27, RED, true));
        c.addView(label(nextTeamMatch(myTeam), 15, muted, false));
        Spinner sp = spinner(data.teams, myTeam);
        c.addView(sp);
        addButton(c, "Save Favorite Team", v -> {
            myTeam = sp.getSelectedItem().toString();
            prefs.edit().putString("team", myTeam).apply();
            showMyTeam();
        });

        LinearLayout matches = card();
        matches.addView(label("⚽ Team Matches", 22, text, true));
        for(Match m:data.matches) if(m.home.equals(myTeam)||m.away.equals(myTeam)) {
            matches.addView(label(m.date+" • "+flag(m.home)+" "+m.home+" vs "+flag(m.away)+" "+m.away+" • "+m.venue, 14, muted, false));
        }

        LinearLayout table = card();
        table.addView(label("📊 Current Table Position", 22, text, true));
        List<TeamRow> rows = data.standings().get(data.groupOf(myTeam));
        if(rows!=null) for(int i=0;i<rows.size();i++) if(rows.get(i).team.equals(myTeam)) {
            TeamRow r=rows.get(i);
            table.addView(label("#"+(i+1)+" in Group "+r.group+" • "+r.pts+" pts • GD "+(r.gf-r.ga), 18, RED, true));
        }
    }

    String nextTeamMatch(String team) {
        for(Match m:data.matches) if((m.home.equals(team)||m.away.equals(team)) && m.homeGoals<0 && m.awayGoals<0)
            return "Next match: "+m.date+" • "+flag(m.home)+" "+m.home+" vs "+flag(m.away)+" "+m.away+" • "+m.venue;
        return "All predicted matches for "+team+" are completed.";
    }

    void showCities() {
        setHeader("Host Cities", "World Cup 2026 travel-style guide", "More");
        content.removeAllViews();
        String[] cities = {"Mexico City","Toronto","Los Angeles","New York/New Jersey","Dallas","Miami","Vancouver","Atlanta","Houston","Kansas City","Boston","Seattle"};
        for(String cty:cities){
            LinearLayout c=card();
            c.addView(label("📍 "+cty, 23, text, true));
            c.addView(label("Host city page ready for stadium info, fan tips, match schedule and travel notes.", 15, muted, false));
        }
    }

    void showMore() {
        setHeader("More", "Settings, premium, cities and legal safety", "More");
        bottom.removeAllViews(); navItem("🏠","Home"); navItem("⚽","Matches"); navItem("🏆","Groups"); navItem("🔮","Predict"); navItem("☰","More");

        LinearLayout c = card();
        addButton(c, "My Team", v -> showMyTeam());
        addButton(c, "Statistics", v -> showStats());
        addButton(c, "Host Cities", v -> showCities());
        addButton(c, "Pro Bracket", v -> showBracket());
        addButton(c, dark ? "Switch to Light Mode" : "Switch to Dark Mode", v -> {
            dark = !dark;
            prefs.edit().putBoolean("dark", dark).apply();
            rebuildCurrent();
        });
        addButton(c, "Premium / Pro Version", v -> showPremium());

        LinearLayout legal = card();
        legal.addView(label("Version 6.0 Pro Tournament Edition", 22, RED, true));
        legal.addView(label("Independent fan-made app. No official FIFA logo, no official crests, no player photos, no live streaming. Works offline and lets fans build predictions.", 15, muted, false));
    }

    void showStats() {
        setHeader("Statistics", "Premium tournament analytics", "More");
        content.removeAllViews();
        LinearLayout c = card();
        c.addView(label("📊 Tournament Analytics", 26, text, true));
        LinearLayout r = row();
        mini(r, ""+data.playedCount(), "played");
        mini(r, ""+data.goalCount(), "goals");
        mini(r, String.format(Locale.US, "%.2f", data.avgGoals()), "avg goals");
        c.addView(r);
        c.addView(label("Best attack: "+data.bestAttack(), 17, RED, true));
        c.addView(label("Most wins: "+data.mostWins(), 17, RED, true));
        c.addView(label("Prediction progress: "+pctCompleted()+"%", 17, GREEN, true));

        LinearLayout leaders = card();
        leaders.addView(label("🏆 Group Winners", 23, text, true));
        Map<String,List<TeamRow>> st=data.standings();
        for(String g:data.groups){
            TeamRow w=st.get(g).get(0);
            leaders.addView(label("Group "+g+": "+flag(w.team)+" "+w.team+" • "+w.pts+" pts", 14, muted, false));
        }
    }

    void showPremium() {
        setHeader("Premium", "Positioning for monetization", "More");
        content.removeAllViews();
        LinearLayout hero = card();
        hero.addView(label("💎 Pro Version", 28, GOLD, true));
        hero.addView(label("Suggested price: €1.99 one-time unlock", 18, RED, true));
        hero.addView(label("This is the screen that sells the value: shareable predictions, multiple brackets, custom tournaments, PDF/PNG export, advanced stats, premium themes and home widgets.", 15, muted, false));

        LinearLayout compare = card();
        compare.addView(label("Free vs Pro", 24, text, true));
        compare.addView(label("FREE\n✓ Basic scores\n✓ Group tables\n✓ Favorite team\n\nPRO\n✓ Multiple saved predictions\n✓ Share prediction poster\n✓ Advanced bracket\n✓ Export PDF/PNG\n✓ Premium themes\n✓ Custom tournaments\n✓ Widgets and reminders", 16, muted, false));
        addButton(compare, "Unlock Pro - Coming Soon", v -> toast("Payment module ready for next version"));
    }

    void sharePrediction() {
        StringBuilder m = new StringBuilder();
        m.append("⚽ My World Cup Fan 2026 prediction\n\n");
        m.append("Favorite team: ").append(flag(myTeam)).append(" ").append(myTeam).append("\n");
        m.append("Progress: ").append(pctCompleted()).append("%\n\n");
        m.append("Qualified teams preview:");
        List<TeamRow> q = data.qualified();
        for(int i=0;i<Math.min(12,q.size());i++){
            m.append("\n").append(i+1).append(". ").append(flag(q.get(i).team)).append(" ").append(q.get(i).team);
        }
        m.append("\n\nBuilt with World Cup Fan 2026 Pro Tournament Edition");
        shareText(m.toString());
    }

    void shareText(String msg) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(send, "Share"));
    }

    void hideKeyboard(View v) {
        try { ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(),0); } catch(Exception e){}
    }

    void toast(String s){ Toast.makeText(this,s,Toast.LENGTH_SHORT).show(); }

    String flag(String t) {
        if(t==null) return "🏳";
        if(t.equals("Croatia")) return "🇭🇷"; if(t.equals("Brazil")) return "🇧🇷"; if(t.equals("Germany")) return "🇩🇪";
        if(t.equals("Argentina")) return "🇦🇷"; if(t.equals("France")) return "🇫🇷"; if(t.equals("Spain")) return "🇪🇸";
        if(t.equals("Portugal")) return "🇵🇹"; if(t.equals("England")) return "🏴"; if(t.equals("Netherlands")) return "🇳🇱";
        if(t.equals("Belgium")) return "🇧🇪"; if(t.equals("Mexico")) return "🇲🇽"; if(t.equals("USA")) return "🇺🇸";
        if(t.equals("Canada")) return "🇨🇦"; if(t.equals("Japan")) return "🇯🇵"; if(t.equals("Korea Republic")) return "🇰🇷";
        if(t.equals("Morocco")) return "🇲🇦"; if(t.equals("Uruguay")) return "🇺🇾"; if(t.equals("Switzerland")) return "🇨🇭";
        if(t.equals("Denmark")) return "🇩🇰"; if(t.equals("Australia")) return "🇦🇺"; if(t.equals("South Africa")) return "🇿🇦";
        if(t.equals("Qatar")) return "🇶🇦"; if(t.equals("Scotland")) return "🏴"; if(t.equals("Paraguay")) return "🇵🇾";
        if(t.equals("Ecuador")) return "🇪🇨"; if(t.equals("Ivory Coast")) return "🇨🇮"; if(t.equals("Tunisia")) return "🇹🇳";
        if(t.equals("Egypt")) return "🇪🇬"; if(t.equals("Norway")) return "🇳🇴"; if(t.equals("Saudi Arabia")) return "🇸🇦";
        if(t.equals("Senegal")) return "🇸🇳"; if(t.equals("Iran")) return "🇮🇷"; if(t.equals("Austria")) return "🇦🇹";
        if(t.equals("Algeria")) return "🇩🇿"; if(t.equals("Colombia")) return "🇨🇴"; if(t.equals("Ghana")) return "🇬🇭";
        if(t.equals("Panama")) return "🇵🇦";
        return "🏳";
    }

    Drawable grad(int a, int b, int radius) {
        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{a,b});
        g.setCornerRadius(radius);
        return g;
    }

    Drawable round(int color, int radius, int strokeColor, int sw) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(radius);
        if(sw>0) g.setStroke(sw, strokeColor);
        return g;
    }

    int dp(int v){ return (int)(v*getResources().getDisplayMetrics().density+0.5f); }

    static class Match {
        String group, home, away, date, venue; int homeGoals=-1, awayGoals=-1;
        Match(String g,String h,String a,String d,String v){group=g;home=h;away=a;date=d;venue=v;}
    }

    static class TeamRow {
        String group, team; int p,w,d,l,gf,ga,pts;
        TeamRow(String g,String t){group=g;team=t;}
    }

    class Data {
        String[] groups={"A","B","C","D","E","F","G","H","I","J","K","L"};
        ArrayList<String> teams=new ArrayList<>();
        ArrayList<Match> matches=new ArrayList<>();
        SharedPreferences sp;

        Data(Context c){ sp=c.getSharedPreferences("scores",0); seed(); load(); }

        void seed(){
            String[][] g={{"Mexico","South Africa","Croatia","Korea Republic"},{"Canada","Switzerland","Qatar","TBD B4"},{"Brazil","Morocco","Scotland","TBD C4"},{"USA","Australia","Paraguay","TBD D4"},{"Germany","Ecuador","Ivory Coast","TBD E4"},{"Netherlands","Japan","Tunisia","TBD F4"},{"Belgium","Egypt","Norway","TBD G4"},{"Spain","Uruguay","Saudi Arabia","TBD H4"},{"France","Senegal","Iran","TBD I4"},{"Argentina","Austria","Algeria","TBD J4"},{"England","Colombia","Ghana","TBD K4"},{"Portugal","Denmark","Panama","TBD L4"}};
            String[] venues={"Mexico City","Toronto","Los Angeles","New York/New Jersey","Dallas","Miami","Vancouver","Atlanta","Houston","Kansas City","Boston","Seattle"};
            int day=11;
            for(int x=0;x<g.length;x++){
                String gr=groups[x]; for(String t:g[x]) teams.add(t); String[] t=g[x];
                matches.add(new Match(gr,t[0],t[1],"2026-06-"+(day+x%15),venues[x]));
                matches.add(new Match(gr,t[2],t[3],"2026-06-"+(day+1+x%15),venues[(x+3)%venues.length]));
                matches.add(new Match(gr,t[0],t[2],"2026-06-"+(day+6+x%12),venues[(x+6)%venues.length]));
                matches.add(new Match(gr,t[1],t[3],"2026-06-"+(day+7+x%12),venues[(x+8)%venues.length]));
                matches.add(new Match(gr,t[0],t[3],"2026-06-"+(day+12+x%7),venues[(x+9)%venues.length]));
                matches.add(new Match(gr,t[1],t[2],"2026-06-"+(day+12+x%7),venues[(x+10)%venues.length]));
            }
            Collections.sort(teams);
        }

        void load(){ for(int i=0;i<matches.size();i++){ matches.get(i).homeGoals=sp.getInt("h"+i,-1); matches.get(i).awayGoals=sp.getInt("a"+i,-1); } }
        void save(){ SharedPreferences.Editor e=sp.edit(); for(int i=0;i<matches.size();i++){ e.putInt("h"+i,matches.get(i).homeGoals); e.putInt("a"+i,matches.get(i).awayGoals); } e.apply(); }

        int playedCount(){ int c=0; for(Match m:matches) if(m.homeGoals>=0&&m.awayGoals>=0)c++; return c; }
        int goalCount(){ int c=0; for(Match m:matches) if(m.homeGoals>=0&&m.awayGoals>=0)c+=m.homeGoals+m.awayGoals; return c; }
        double avgGoals(){ return playedCount()==0?0:(goalCount()*1.0/playedCount()); }

        void reset(){ sp.edit().clear().apply(); for(Match m:matches){m.homeGoals=-1;m.awayGoals=-1;} }

        String groupOf(String team){ for(Match m:matches) if(m.home.equals(team)||m.away.equals(team)) return m.group; return "A"; }

        Map<String,List<TeamRow>> standings(){
            Map<String,List<TeamRow>> out=new LinkedHashMap<>();
            for(String gr:groups){
                Map<String,TeamRow> map=new LinkedHashMap<>();
                for(Match ma:matches) if(ma.group.equals(gr)){
                    map.putIfAbsent(ma.home,new TeamRow(gr,ma.home)); map.putIfAbsent(ma.away,new TeamRow(gr,ma.away));
                    if(ma.homeGoals>=0&&ma.awayGoals>=0){
                        TeamRow h=map.get(ma.home), a=map.get(ma.away);
                        h.p++; a.p++; h.gf+=ma.homeGoals; h.ga+=ma.awayGoals; a.gf+=ma.awayGoals; a.ga+=ma.homeGoals;
                        if(ma.homeGoals>ma.awayGoals){h.w++;h.pts+=3;a.l++;}
                        else if(ma.homeGoals<ma.awayGoals){a.w++;a.pts+=3;h.l++;}
                        else {h.d++;a.d++;h.pts++;a.pts++;}
                    }
                }
                ArrayList<TeamRow> list=new ArrayList<>(map.values());
                Collections.sort(list,(x,y)-> y.pts!=x.pts?y.pts-x.pts:((y.gf-y.ga)!=(x.gf-x.ga)?(y.gf-y.ga)-(x.gf-x.ga):y.gf-x.gf));
                out.put(gr,list);
            }
            return out;
        }

        List<TeamRow> bestThirds(){
            ArrayList<TeamRow> t=new ArrayList<>();
            for(List<TeamRow> l:standings().values()) if(l.size()>2)t.add(l.get(2));
            Collections.sort(t,(x,y)-> y.pts!=x.pts?y.pts-x.pts:((y.gf-y.ga)!=(x.gf-x.ga)?(y.gf-y.ga)-(x.gf-x.ga):y.gf-x.gf));
            return t;
        }

        List<TeamRow> qualified(){
            ArrayList<TeamRow> q=new ArrayList<>();
            for(List<TeamRow> l:standings().values()){ if(l.size()>0)q.add(l.get(0)); if(l.size()>1)q.add(l.get(1)); }
            List<TeamRow> th=bestThirds(); for(int i=0;i<Math.min(8,th.size());i++)q.add(th.get(i));
            return q;
        }

        String bestAttack(){
            Map<String,Integer> gf=new HashMap<>();
            for(Match m:matches) if(m.homeGoals>=0&&m.awayGoals>=0){gf.put(m.home,gf.getOrDefault(m.home,0)+m.homeGoals);gf.put(m.away,gf.getOrDefault(m.away,0)+m.awayGoals);}
            String b="TBD"; int max=-1; for(String t:gf.keySet()) if(gf.get(t)>max){max=gf.get(t);b=t;}
            return b.equals("TBD")?"TBD":b+" ("+max+")";
        }

        String mostWins(){
            String b="TBD"; int max=-1;
            for(List<TeamRow> l:standings().values()) for(TeamRow r:l) if(r.w>max){max=r.w;b=r.team;}
            return b.equals("TBD")?"TBD":b+" ("+max+")";
        }

        String championGuess(){
            List<TeamRow> q=qualified();
            if(q.size()==0)return "TBD";
            for(TeamRow r:q) if(r.team.equals(myTeam)) return myTeam;
            return q.get(0).team;
        }
    }
}
