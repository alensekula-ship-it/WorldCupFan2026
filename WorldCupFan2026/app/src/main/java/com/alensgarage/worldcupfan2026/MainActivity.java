package com.alensgarage.worldcupfan2026;

import android.app.*;
import android.os.*;
import android.content.*;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import android.text.InputType;
import java.text.*;
import java.util.*;

public class MainActivity extends Activity {
    final int RED = Color.rgb(220, 0, 48);
    final int RED_DARK = Color.rgb(92, 0, 20);
    final int GOLD = Color.rgb(248, 197, 55);
    final int GREEN = Color.rgb(0, 158, 96);
    final int BLUE = Color.rgb(35, 102, 235);
    int bg, cardBg, text, subText, navBg, chipBg, stroke, softRed;
    LinearLayout root, content, bottom;
    TextView title, subtitle;
    Data data;
    Handler handler = new Handler();
    SharedPreferences prefs;
    boolean darkMode;
    String currentScreen = "Home";
    String myTeam = "Croatia";
    String matchFilter = "";
    String groupFilter = "All";

    public void onCreate(Bundle b) {
        super.onCreate(b);
        prefs = getSharedPreferences("worldcupfan2026", 0);
        darkMode = prefs.getBoolean("darkMode", false);
        myTeam = prefs.getString("team", "Croatia");
        data = new Data(this);
        applyTheme();
        build();
        showHome();
    }

    void applyTheme() {
        bg = darkMode ? Color.rgb(7,9,14) : Color.rgb(245,246,249);
        cardBg = darkMode ? Color.rgb(24,27,37) : Color.WHITE;
        text = darkMode ? Color.WHITE : Color.rgb(22,24,32);
        subText = darkMode ? Color.rgb(198,202,214) : Color.rgb(85,88,98);
        navBg = darkMode ? Color.rgb(15,18,26) : Color.WHITE;
        chipBg = darkMode ? Color.rgb(34,38,52) : Color.rgb(238,240,245);
        stroke = darkMode ? Color.rgb(58,62,78) : Color.rgb(224,226,232);
        softRed = darkMode ? Color.rgb(62,18,30) : Color.rgb(255,235,240);
    }

    void build() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);
        setContentView(root);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setPadding(dp(20), dp(22), dp(20), dp(12));
        header.setBackground(gradient(RED_DARK, RED, dp(0)));
        root.addView(header, new LinearLayout.LayoutParams(-1, dp(132)));

        title = label("World Cup Fan 2026", 27, Color.WHITE, true);
        subtitle = label("v8.0 Play Store Edition • Pro Bracket • Score Picker • Share Poster", 14, Color.WHITE, false);
        header.addView(title);
        header.addView(subtitle);

        ScrollView sv = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(12), dp(12), dp(12), dp(96));
        sv.addView(content);
        root.addView(sv, new LinearLayout.LayoutParams(-1, 0, 1));

        bottom = new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setPadding(dp(8), dp(6), dp(8), dp(6));
        bottom.setBackgroundColor(navBg);
        root.addView(bottom, new LinearLayout.LayoutParams(-1, dp(76)));
        nav("🏠", "Home");
        nav("⚽", "Matches");
        nav("🏆", "Groups");
        nav("🔮", "Predict");
        nav("☰", "More");
    }

    void nav(String icon, String name) {
        boolean selected = currentScreen.equals(name) || (currentScreen.equals("Predict") && name.equals("Predict"));
        TextView b = label(icon + "\n" + name, 12, selected ? Color.WHITE : text, true);
        b.setGravity(Gravity.CENTER);
        b.setBackground(selected ? gradient(RED_DARK, RED, dp(18)) : round(chipBg, dp(18), 0));
        b.setOnClickListener(v -> {
            if (name.equals("Home")) showHome();
            else if (name.equals("Matches")) showMatches();
            else if (name.equals("Groups")) showGroups();
            else if (name.equals("Predict")) showPredictor();
            else showMore();
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -1, 1);
        lp.setMargins(dp(4), 0, dp(4), 0);
        bottom.addView(b, lp);
    }

    void redraw() {
        applyTheme();
        build();
        if (currentScreen.equals("Home")) showHome();
        else if (currentScreen.equals("Matches")) showMatches();
        else if (currentScreen.equals("Groups")) showGroups();
        else if (currentScreen.equals("Predict")) showPredictor();
        else showMore();
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

    Button btn(String s) {
        Button b = new Button(this);
        b.setText(s);
        b.setTextSize(14);
        b.setTextColor(Color.WHITE);
        b.setAllCaps(false);
        b.setTypeface(Typeface.DEFAULT_BOLD);
        b.setPadding(dp(6),0,dp(6),0);
        b.setBackground(gradient(RED_DARK, RED, dp(18)));
        return b;
    }

    Button outlineBtn(String s) {
        Button b = new Button(this);
        b.setText(s);
        b.setTextSize(13);
        b.setTextColor(text);
        b.setAllCaps(false);
        b.setTypeface(Typeface.DEFAULT_BOLD);
        b.setBackground(round(chipBg, dp(18), 1));
        return b;
    }

    TextView chip(String s) {
        TextView t = label(s, 13, text, true);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(10), dp(8), dp(10), dp(8));
        t.setBackground(round(chipBg, dp(18), 1));
        return t;
    }

    LinearLayout card() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(16), dp(16), dp(16), dp(16));
        l.setBackground(round(cardBg, dp(24), 1));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 0, 0, dp(12));
        content.addView(l, lp);
        return l;
    }

    LinearLayout innerCard(LinearLayout parent) {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(12), dp(10), dp(12), dp(10));
        l.setBackground(round(chipBg, dp(18), 0));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, dp(8), 0, dp(8));
        parent.addView(l, lp);
        return l;
    }

    LinearLayout hrow() {
        LinearLayout r = new LinearLayout(this);
        r.setOrientation(LinearLayout.HORIZONTAL);
        r.setGravity(Gravity.CENTER_VERTICAL);
        return r;
    }

    void clear(String h, String s, String key) {
        currentScreen = key;
        title.setText(h);
        subtitle.setText(s);
        content.removeAllViews();
    }

    void showHome() {
        clear("World Cup Fan 2026", "v8.0 • Play Store Edition", "Home");

        LinearLayout hero = card();
        hero.setBackground(gradient(RED_DARK, RED, dp(24)));
        hero.addView(label("⚽ Play Store Edition v8.0", 24, Color.WHITE, true));
        hero.addView(label("Professional tournament predictor, pro bracket, clean score picker and share-ready fan poster.", 15, Color.WHITE, false));
        hero.addView(kpiRow("Progress", data.progressPercent() + "%", "Played", "" + data.playedCount(), "Goals", "" + data.totalGoals(), true));

        LinearLayout c = card();
        c.addView(label("Kick-off countdown", 17, subText, true));
        TextView count = label("", 35, RED, true);
        c.addView(count);
        Runnable r = new Runnable() {
            public void run() {
                String d = daysTo("2026-06-11 21:00");
                count.setText(d + " " + (d.equals("1") ? "day" : "days") + " to opening");
                handler.postDelayed(this, 60000);
            }
        };
        r.run();

        LinearLayout my = card();
        my.addView(label(flag(myTeam) + " My Team", 23, text, true));
        Match next = data.nextFor(myTeam);
        my.addView(label(next == null ? "Pick your team and build the path to the final." : "Next match: " + next.date + " • " + next.home + " vs " + next.away, 15, subText, false));
        LinearLayout actions = hrow();
        Button open = btn("Team Hub");
        open.setOnClickListener(v -> showMyTeam());
        Button poster = outlineBtn("Share Poster");
        poster.setOnClickListener(v -> showPoster());
        actions.addView(open, new LinearLayout.LayoutParams(0, dp(52), 1));
        actions.addView(poster, new LinearLayout.LayoutParams(0, dp(52), 1));
        my.addView(actions);

        LinearLayout studio = card();
        studio.addView(label("🔮 Prediction Studio", 22, text, true));
        studio.addView(label("Enter scores, generate tables, fill knockout, pick champion and share the story.", 15, subText, false));
        Button start = btn("Start Simulator");
        start.setOnClickListener(v -> showPredictor());
        studio.addView(start);

        LinearLayout tips = card();
        tips.addView(label("🚀 Why fans install it", 21, text, true));
        tips.addView(label("• Offline predictor\n• My Team mode\n• Group tables\n• Pro bracket\n• Share poster\n• Dream Final builder\n• Host city guide", 15, subText, false));
    }

    LinearLayout kpiRow(String a, String av, String b, String bv, String c, String cv, boolean light) {
        LinearLayout r = hrow();
        r.setPadding(0, dp(10), 0, 0);
        r.addView(kpi(a, av, light), new LinearLayout.LayoutParams(0, dp(72), 1));
        r.addView(kpi(b, bv, light), new LinearLayout.LayoutParams(0, dp(72), 1));
        r.addView(kpi(c, cv, light), new LinearLayout.LayoutParams(0, dp(72), 1));
        return r;
    }

    LinearLayout kpi(String name, String val, boolean light) {
        LinearLayout k = new LinearLayout(this);
        k.setOrientation(LinearLayout.VERTICAL);
        k.setGravity(Gravity.CENTER);
        k.setPadding(dp(4), dp(4), dp(4), dp(4));
        k.setBackground(round(light ? Color.argb(45,255,255,255) : chipBg, dp(18), 0));
        TextView v = label(val, 22, light ? Color.WHITE : RED, true);
        v.setGravity(Gravity.CENTER);
        TextView n = label(name, 11, light ? Color.WHITE : subText, false);
        n.setGravity(Gravity.CENTER);
        k.addView(v);
        k.addView(n);
        return k;
    }

    String daysTo(String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(date);
            long diff = d.getTime() - System.currentTimeMillis();
            return String.valueOf(Math.max(0, diff / 86400000));
        } catch(Exception e) { return "0"; }
    }

    void showMatches() {
        clear("Matches", "Professional score picker and filters", "Matches");

        LinearLayout top = card();
        top.addView(label("🔎 Match Center", 21, text, true));
        EditText search = new EditText(this);
        search.setHint("Search team, group or host city");
        search.setText(matchFilter);
        search.setSingleLine(true);
        search.setTextColor(text);
        search.setHintTextColor(subText);
        search.setPadding(dp(14),0,dp(14),0);
        search.setBackground(round(chipBg, dp(16), 0));
        top.addView(search, new LinearLayout.LayoutParams(-1, dp(54)));

        LinearLayout filters1 = hrow();
        String[] chips1 = {"All","A","B","C","D","E","F"};
        for (String g: chips1) addGroupChip(filters1, g, search);
        top.addView(filters1);

        LinearLayout filters2 = hrow();
        String[] chips2 = {"G","H","I","J","K","L"};
        for (String g: chips2) addGroupChip(filters2, g, search);
        top.addView(filters2);

        Button go = btn("Apply Search");
        go.setOnClickListener(v -> { matchFilter = search.getText().toString(); showMatches(); });
        top.addView(go);

        String f = matchFilter.toLowerCase(Locale.US).trim();
        int shown = 0;
        for (Match m : data.matches) {
            if (!groupFilter.equals("All") && !m.group.equals(groupFilter)) continue;
            String hay = (m.group + " " + m.home + " " + m.away + " " + m.venue).toLowerCase(Locale.US);
            if (f.length() > 0 && !hay.contains(f)) continue;
            shown++;
            matchCard(m);
        }
        if (shown == 0) {
            LinearLayout n = card();
            n.addView(label("No matches found", 21, text, true));
            n.addView(label("Try another team, group or city.", 15, subText, false));
        }
    }

    void addGroupChip(LinearLayout row, String g, EditText search) {
        TextView ch = chip(g.equals("All") ? "All" : g);
        boolean sel = g.equals(groupFilter);
        ch.setTextColor(sel ? Color.WHITE : text);
        if (sel) ch.setBackground(gradient(RED_DARK, RED, dp(18)));
        ch.setOnClickListener(v -> { groupFilter = g; matchFilter = search.getText().toString(); showMatches(); });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, dp(42), 1);
        lp.setMargins(dp(2), dp(5), dp(2), dp(1));
        row.addView(ch, lp);
    }

    void matchCard(Match m) {
        LinearLayout c = card();
        c.addView(label("Group " + m.group + " • " + m.date + " • " + m.venue, 12, subText, true));

        LinearLayout teams = hrow();
        TextView left = label(flag(m.home) + "\n" + shortName(m.home), 15, text, true);
        left.setGravity(Gravity.CENTER);
        TextView score = label((m.homeGoals>=0?m.homeGoals:"-") + " : " + (m.awayGoals>=0?m.awayGoals:"-"), 30, RED, true);
        score.setGravity(Gravity.CENTER);
        TextView right = label(flag(m.away) + "\n" + shortName(m.away), 15, text, true);
        right.setGravity(Gravity.CENTER);
        teams.addView(left, new LinearLayout.LayoutParams(0, dp(80), 1));
        teams.addView(score, new LinearLayout.LayoutParams(0, dp(80), 1));
        teams.addView(right, new LinearLayout.LayoutParams(0, dp(80), 1));
        c.addView(teams);

        LinearLayout controls = hrow();
        TextView homeScore = scoreBoxLabel(m.homeGoals);
        TextView awayScore = scoreBoxLabel(m.awayGoals);

        Button hm = outlineBtn("−"); hm.setOnClickListener(v -> { m.homeGoals = change(m.homeGoals, -1); homeScore.setText(displayScore(m.homeGoals)); });
        Button hp = outlineBtn("+"); hp.setOnClickListener(v -> { m.homeGoals = change(m.homeGoals, 1); homeScore.setText(displayScore(m.homeGoals)); });
        Button am = outlineBtn("−"); am.setOnClickListener(v -> { m.awayGoals = change(m.awayGoals, -1); awayScore.setText(displayScore(m.awayGoals)); });
        Button ap = outlineBtn("+"); ap.setOnClickListener(v -> { m.awayGoals = change(m.awayGoals, 1); awayScore.setText(displayScore(m.awayGoals)); });

        controls.addView(hm, new LinearLayout.LayoutParams(0, dp(48), 1));
        controls.addView(homeScore, new LinearLayout.LayoutParams(0, dp(48), 1));
        controls.addView(hp, new LinearLayout.LayoutParams(0, dp(48), 1));
        controls.addView(am, new LinearLayout.LayoutParams(0, dp(48), 1));
        controls.addView(awayScore, new LinearLayout.LayoutParams(0, dp(48), 1));
        controls.addView(ap, new LinearLayout.LayoutParams(0, dp(48), 1));
        c.addView(controls);

        Button save = btn("Save Result");
        save.setOnClickListener(v -> { data.save(); toast("Result saved"); showMatches(); });
        c.addView(save);
    }

    int change(int val, int delta) {
        if (val < 0) val = 0;
        val += delta;
        if (val < 0) val = 0;
        return val;
    }

    String displayScore(int v) { return v >= 0 ? "" + v : "-"; }

    TextView scoreBoxLabel(int val) {
        TextView e = label(displayScore(val), 21, text, true);
        e.setGravity(Gravity.CENTER);
        e.setBackground(round(chipBg, dp(14), 0));
        return e;
    }

    String shortName(String s) {
        if (s.startsWith("TBD")) return s;
        return s.length() > 12 ? s.substring(0, 12) : s;
    }

    void showGroups() {
        clear("Groups", "Clean tournament standings", "Groups");
        Map<String,List<TeamRow>> map = data.standings();
        for (String g: data.groups) {
            LinearLayout c = card();
            c.addView(label("Group " + g, 23, RED, true));
            LinearLayout head = tableRow("#", "Team", "P", "W", "D", "L", "GD", "Pts", true, subText);
            c.addView(head);
            List<TeamRow> rows = map.get(g);
            for (int i=0;i<rows.size();i++) {
                TeamRow r = rows.get(i);
                int color = i < 2 ? GREEN : (i == 2 ? GOLD : subText);
                c.addView(tableRow((i+1)+"", flag(r.team)+" "+r.team, r.p+"", r.w+"", r.d+"", r.l+"", (r.gf-r.ga)+"", r.pts+"", false, color));
            }
        }
        LinearLayout third = card();
        third.addView(label("🥉 Best third-placed teams", 22, text, true));
        List<TeamRow> th = data.bestThirds();
        for (int i=0;i<th.size();i++) {
            TeamRow r = th.get(i);
            third.addView(label((i+1)+". "+flag(r.team)+" "+r.team+" • Group "+r.group+" • "+r.pts+" pts", 14, i<8 ? GREEN : subText, false));
        }
    }

    LinearLayout tableRow(String pos, String team, String p, String w, String d, String l, String gd, String pts, boolean bold, int color) {
        LinearLayout r = hrow();
        r.setPadding(0, dp(4), 0, dp(4));
        r.addView(cell(pos, 0.45f, bold, color));
        r.addView(cell(team, 2.4f, bold, color));
        r.addView(cell(p, 0.55f, bold, color));
        r.addView(cell(w, 0.55f, bold, color));
        r.addView(cell(d, 0.55f, bold, color));
        r.addView(cell(l, 0.55f, bold, color));
        r.addView(cell(gd, 0.7f, bold, color));
        r.addView(cell(pts, 0.8f, true, color));
        return r;
    }

    TextView cell(String s, float weight, boolean bold, int color) {
        TextView t = label(s, 12, color, bold);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setSingleLine(true);
        t.setTextSize(s.length() > 14 ? 11 : 12);
        t.setLayoutParams(new LinearLayout.LayoutParams(0, dp(34), weight));
        return t;
    }

    void showPredictor() {
        clear("Predictor", "Studio, bracket and share poster", "Predict");
        LinearLayout c = card();
        c.addView(label("🔮 Prediction Studio", 25, text, true));
        c.addView(label("A clean tournament studio built for football fans: enter scores, generate tables, bracket, champion pick and share poster.", 15, subText, false));
        Button scores = btn("Enter Scores");
        scores.setOnClickListener(v -> showMatches());
        c.addView(scores);
        Button br = btn("Pro Bracket");
        br.setOnClickListener(v -> showKnockout());
        c.addView(br);
        Button poster = btn("Share Poster");
        poster.setOnClickListener(v -> showPoster());
        c.addView(poster);
        Button dream = btn("Dream Final");
        dream.setOnClickListener(v -> showDreamFinal());
        c.addView(dream);
        Button reset = outlineBtn("Reset Scores");
        reset.setOnClickListener(v -> { data.reset(); showPredictor(); });
        c.addView(reset);

        LinearLayout q = card();
        q.addView(label("✅ Qualified teams preview", 22, text, true));
        List<TeamRow> qual = data.qualified();
        for (int i=0;i<Math.min(qual.size(), 16);i++) {
            TeamRow r = qual.get(i);
            q.addView(label((i+1)+". "+flag(r.team)+" "+r.team+" • Group "+r.group+" • "+r.pts+" pts", 14, subText, false));
        }
    }

    void showKnockout() {
        clear("Pro Bracket", "Premium tournament path", "Predict");
        List<TeamRow> q = data.qualified();

        LinearLayout intro = card();
        intro.setBackground(gradient(RED_DARK, RED, dp(24)));
        intro.addView(label("🏆 Knockout Path", 26, Color.WHITE, true));
        intro.addView(label("Projected bracket based on your group results.", 15, Color.WHITE, false));

        String[] rounds = {"Round of 32", "Round of 16", "Quarter-finals", "Semi-finals", "Final"};
        int games = 16;
        for (String round: rounds) {
            LinearLayout c = card();
            c.addView(label(round, 23, RED, true));
            for (int i=0; i<games; i++) {
                String a = q.size()>0 ? q.get((i*2)%q.size()).team : "TBD";
                String b = q.size()>1 ? q.get((i*2+1)%q.size()).team : "TBD";
                String winner = pickWinner(a, b);
                LinearLayout match = innerCard(c);
                match.addView(label(flag(a)+" "+a, 15, text, true));
                match.addView(label("        │", 12, subText, false));
                match.addView(label(flag(b)+" "+b, 15, text, true));
                match.addView(label("Projected winner: "+flag(winner)+" "+winner, 13, GREEN, true));
            }
            games = Math.max(1, games/2);
        }

        LinearLayout champ = card();
        String ch = q.size()>0 ? q.get(0).team : myTeam;
        champ.setBackground(gradient(RED_DARK, RED, dp(24)));
        champ.addView(label("🏆 Champion Pick", 23, Color.WHITE, true));
        champ.addView(label(flag(ch)+" "+ch, 36, GOLD, true));
    }

    String pickWinner(String a, String b) {
        if (a.equals(myTeam)) return a;
        if (b.equals(myTeam)) return b;
        return a.compareTo(b) <= 0 ? a : b;
    }

    void showPoster() {
        clear("Share Poster", "Designed for social media", "Predict");
        LinearLayout p = card();
        p.setBackground(gradient(RED_DARK, RED, dp(24)));
        p.addView(label("WORLD CUP FAN 2026", 25, Color.WHITE, true));
        p.addView(label("MY TOURNAMENT PREDICTION", 17, Color.WHITE, false));
        p.addView(label(flag(myTeam)+" My team: "+myTeam, 22, Color.WHITE, true));
        List<TeamRow> q = data.qualified();
        String champ = q.size()>0 ? q.get(0).team : myTeam;
        p.addView(label("🏆 Champion: "+flag(champ)+" "+champ, 25, GOLD, true));
        p.addView(label("Top 4\n"+topTeamsLines(4), 16, Color.WHITE, false));
        p.addView(label("Made with World Cup Fan 2026", 13, Color.WHITE, false));

        Button share = btn("Share Prediction Text");
        share.setOnClickListener(v -> sharePrediction());
        p.addView(share);

        LinearLayout note = card();
        note.addView(label("Next upgrade", 19, text, true));
        note.addView(label("The visual poster layout is ready. In a later build we can export this as a real PNG image for Instagram/Facebook.", 15, subText, false));
    }

    String topTeamsLines(int n) {
        List<TeamRow> q = data.qualified();
        String s = "";
        for (int i=0;i<Math.min(n,q.size());i++) s += (i+1)+". "+flag(q.get(i).team)+" "+q.get(i).team+"\n";
        return s.length()==0 ? "TBD" : s;
    }

    String topTeams(int n) {
        List<TeamRow> q = data.qualified();
        String s = "";
        for (int i=0;i<Math.min(n,q.size());i++) {
            if (i>0) s += " • ";
            s += flag(q.get(i).team)+" "+q.get(i).team;
        }
        return s.length()==0 ? "TBD" : s;
    }

    void showDreamFinal() {
        clear("Dream Final", "Build a fan final", "Predict");
        LinearLayout c = card();
        c.addView(label("🏆 Dream Final Builder", 25, text, true));
        Spinner left = spinner(data.teams, myTeam);
        Spinner right = spinner(data.teams, "Brazil");
        c.addView(label("Finalist 1", 13, subText, true));
        c.addView(left);
        c.addView(label("Finalist 2", 13, subText, true));
        c.addView(right);
        Button share = btn("Share Dream Final");
        share.setOnClickListener(v -> {
            String a = left.getSelectedItem().toString();
            String b = right.getSelectedItem().toString();
            shareText("🏆 My Dream World Cup 2026 Final\n\n"+flag(a)+" "+a+" vs "+flag(b)+" "+b+"\n\nMade with World Cup Fan 2026");
        });
        c.addView(share);
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
        clear("My Team", "Personal team hub", "More");
        LinearLayout c = card();
        c.addView(label(flag(myTeam)+" "+myTeam, 28, RED, true));
        Spinner sp = spinner(data.teams, myTeam);
        c.addView(sp);
        Button save = btn("Save My Team");
        save.setOnClickListener(v -> { myTeam=sp.getSelectedItem().toString(); prefs.edit().putString("team", myTeam).apply(); showMyTeam(); });
        c.addView(save);

        LinearLayout m = card();
        m.addView(label("⚽ Team matches", 22, text, true));
        for (Match ma: data.matches) {
            if (ma.home.equals(myTeam)||ma.away.equals(myTeam)) m.addView(label(ma.date+" • "+flag(ma.home)+" "+ma.home+" vs "+flag(ma.away)+" "+ma.away, 14, subText, false));
        }
    }

    void showCities() {
        clear("Host Cities", "USA • Mexico • Canada", "More");
        String[][] cities = {
            {"🇲🇽 Mexico City","Opening match energy and historic football culture."},
            {"🇨🇦 Toronto","Canada showcase city."},
            {"🇺🇸 Los Angeles","Entertainment capital and massive fan base."},
            {"🇺🇸 New York/New Jersey","Final atmosphere and huge global audience."},
            {"🇺🇸 Dallas","Huge stadium, huge matches."},
            {"🇺🇸 Miami","Latin football culture and beach city vibe."},
            {"🇨🇦 Vancouver","West coast Canadian host."},
            {"🇺🇸 Atlanta","Modern stadium and fan festival city."},
            {"🇺🇸 Houston","International city with strong football community."},
            {"🇺🇸 Kansas City","American soccer heartland."},
            {"🇺🇸 Boston","Historic city, passionate sports fans."},
            {"🇺🇸 Seattle","Strong supporter culture."}
        };
        for (String[] ci: cities) {
            LinearLayout c = card();
            c.addView(label(ci[0], 22, text, true));
            c.addView(label(ci[1], 15, subText, false));
        }
    }

    void showMore() {
        clear("More", "Settings, premium and legal safety", "More");
        LinearLayout c = card();
        String[] names = {"My Team","Statistics","Host Cities","Knockout","Premium / Pro Version"};
        for (String n: names) {
            Button b = btn(n);
            if (n.equals("My Team")) b.setOnClickListener(v -> showMyTeam());
            else if (n.equals("Statistics")) b.setOnClickListener(v -> showStats());
            else if (n.equals("Host Cities")) b.setOnClickListener(v -> showCities());
            else if (n.equals("Knockout")) b.setOnClickListener(v -> showKnockout());
            else b.setOnClickListener(v -> showPremium());
            c.addView(b);
        }
        Button mode = btn(darkMode ? "Switch to Light Mode" : "Switch to Dark Mode");
        mode.setOnClickListener(v -> { darkMode=!darkMode; prefs.edit().putBoolean("darkMode", darkMode).apply(); redraw(); });
        c.addView(mode);

        LinearLayout l = card();
        l.addView(label("Version 8.0 Play Store Edition", 22, RED, false));
        l.addView(label("Independent fan-made app. No official FIFA logo, no official crests, no player photos, no live streaming. Works offline and lets fans build predictions.", 15, subText, false));
    }

    void showPremium() {
        clear("Premium", "Free vs Pro positioning", "More");
        LinearLayout c = card();
        c.setBackground(gradient(RED_DARK, RED, dp(24)));
        c.addView(label("💎 World Cup Fan Pro", 27, Color.WHITE, true));
        c.addView(label("Suggested price: €1.99", 20, GOLD, true));
        c.addView(label("FREE\n• Basic scores\n• Group tables\n• One prediction\n\nPRO\n• Multiple saved predictions\n• Share poster\n• Advanced statistics\n• Dream finals\n• Premium themes\n• Export PDF/PNG\n• Match reminders\n• Custom tournaments", 16, Color.WHITE, false));
        Button share = btn("Share App");
        share.setOnClickListener(v -> shareText("World Cup Fan 2026 - offline tournament predictor and fan simulator."));
        c.addView(share);
    }

    void showStats() {
        clear("Statistics", "Advanced tournament insights", "More");
        LinearLayout c = card();
        c.addView(label("📊 Tournament stats", 25, text, true));
        c.addView(kpiRow("Played", ""+data.playedCount(), "Goals", ""+data.totalGoals(), "Progress", data.progressPercent()+"%", false));
        c.addView(label("Average goals: "+data.avgGoals(), 18, subText, false));
        c.addView(label("Best attack: "+data.bestAttack(), 18, RED, true));
        c.addView(label("Highest scoring group: "+data.bestGroup(), 18, GREEN, true));
    }

    void sharePrediction() {
        String msg = "⚽ World Cup Fan 2026 Prediction\n\nMy team: "+flag(myTeam)+" "+myTeam+"\nChampion pick: "+topTeams(1)+"\nTop 4: "+topTeams(4)+"\nProgress: "+data.progressPercent()+"%\n\nMade with World Cup Fan 2026";
        shareText(msg);
    }

    void shareText(String msg) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(send, "Share"));
    }

    void toast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

    String flag(String t) {
        if (t == null) return "🏳";
        if (t.equals("Croatia")) return "🇭🇷"; if (t.equals("Brazil")) return "🇧🇷"; if (t.equals("Germany")) return "🇩🇪";
        if (t.equals("Argentina")) return "🇦🇷"; if (t.equals("France")) return "🇫🇷"; if (t.equals("Spain")) return "🇪🇸";
        if (t.equals("Portugal")) return "🇵🇹"; if (t.equals("England")) return "🏴"; if (t.equals("Netherlands")) return "🇳🇱";
        if (t.equals("Belgium")) return "🇧🇪"; if (t.equals("Mexico")) return "🇲🇽"; if (t.equals("USA")) return "🇺🇸";
        if (t.equals("Canada")) return "🇨🇦"; if (t.equals("Japan")) return "🇯🇵"; if (t.equals("Korea Republic")) return "🇰🇷";
        if (t.equals("Morocco")) return "🇲🇦"; if (t.equals("Uruguay")) return "🇺🇾"; if (t.equals("Switzerland")) return "🇨🇭";
        if (t.equals("Denmark")) return "🇩🇰"; if (t.equals("Australia")) return "🇦🇺"; if (t.equals("South Africa")) return "🇿🇦";
        if (t.equals("Qatar")) return "🇶🇦"; if (t.equals("Scotland")) return "🏴"; if (t.equals("Paraguay")) return "🇵🇾";
        if (t.equals("Ecuador")) return "🇪🇨"; if (t.equals("Ivory Coast")) return "🇨🇮"; if (t.equals("Tunisia")) return "🇹🇳";
        if (t.equals("Egypt")) return "🇪🇬"; if (t.equals("Norway")) return "🇳🇴"; if (t.equals("Saudi Arabia")) return "🇸🇦";
        if (t.equals("Senegal")) return "🇸🇳"; if (t.equals("Iran")) return "🇮🇷"; if (t.equals("Austria")) return "🇦🇹";
        if (t.equals("Algeria")) return "🇩🇿"; if (t.equals("Colombia")) return "🇨🇴"; if (t.equals("Ghana")) return "🇬🇭";
        if (t.equals("Panama")) return "🇵🇦";
        return "🏳";
    }

    Drawable gradient(int a, int b, int radius) {
        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{a,b});
        g.setCornerRadius(radius);
        return g;
    }

    Drawable round(int color, int radius, int strokeWidth) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(radius);
        if (strokeWidth > 0) g.setStroke(strokeWidth, stroke);
        return g;
    }

    int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }

    static class Match {
        String group, home, away, date, venue;
        int homeGoals=-1, awayGoals=-1;
        Match(String g, String h, String a, String d, String v){group=g;home=h;away=a;date=d;venue=v;}
    }
    static class TeamRow {
        String group, team; int pts,gf,ga,w,d,l,p;
        TeamRow(String g, String t){group=g;team=t;}
    }
    class Data {
        String[] groups={"A","B","C","D","E","F","G","H","I","J","K","L"};
        ArrayList<String> teams=new ArrayList<>();
        ArrayList<Match> matches=new ArrayList<>();
        SharedPreferences sp;
        Data(Context c){sp=c.getSharedPreferences("scores",0);seed();load();}
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
        void load(){for(int i=0;i<matches.size();i++){matches.get(i).homeGoals=sp.getInt("h"+i,-1);matches.get(i).awayGoals=sp.getInt("a"+i,-1);}}
        void save(){SharedPreferences.Editor e=sp.edit();for(int i=0;i<matches.size();i++){e.putInt("h"+i,matches.get(i).homeGoals);e.putInt("a"+i,matches.get(i).awayGoals);}e.apply();}
        void reset(){sp.edit().clear().apply();for(Match m:matches){m.homeGoals=-1;m.awayGoals=-1;}}
        Map<String,List<TeamRow>> standings(){
            Map<String,List<TeamRow>> out=new LinkedHashMap<>();
            for(String gr:groups){
                Map<String,TeamRow> m=new LinkedHashMap<>();
                for(Match ma:matches)if(ma.group.equals(gr)){
                    m.putIfAbsent(ma.home,new TeamRow(gr,ma.home));m.putIfAbsent(ma.away,new TeamRow(gr,ma.away));
                    if(ma.homeGoals>=0&&ma.awayGoals>=0){
                        TeamRow h=m.get(ma.home),a=m.get(ma.away);h.p++;a.p++;
                        h.gf+=ma.homeGoals;h.ga+=ma.awayGoals;a.gf+=ma.awayGoals;a.ga+=ma.homeGoals;
                        if(ma.homeGoals>ma.awayGoals){h.w++;h.pts+=3;a.l++;}else if(ma.homeGoals<ma.awayGoals){a.w++;a.pts+=3;h.l++;}else{h.d++;a.d++;h.pts++;a.pts++;}
                    }
                }
                ArrayList<TeamRow> list=new ArrayList<>(m.values());
                Collections.sort(list,(x,y)->y.pts!=x.pts?y.pts-x.pts:((y.gf-y.ga)!=(x.gf-x.ga)?(y.gf-y.ga)-(x.gf-x.ga):y.gf-x.gf));
                out.put(gr,list);
            }
            return out;
        }
        List<TeamRow> bestThirds(){ArrayList<TeamRow> third=new ArrayList<>();for(List<TeamRow> l:standings().values())if(l.size()>2)third.add(l.get(2));Collections.sort(third,(x,y)->y.pts!=x.pts?y.pts-x.pts:((y.gf-y.ga)!=(x.gf-x.ga)?(y.gf-y.ga)-(x.gf-x.ga):y.gf-x.gf));return third;}
        List<TeamRow> qualified(){ArrayList<TeamRow> q=new ArrayList<>();for(List<TeamRow> l:standings().values()){if(l.size()>0)q.add(l.get(0));if(l.size()>1)q.add(l.get(1));}List<TeamRow> th=bestThirds();for(int i=0;i<Math.min(8,th.size());i++)q.add(th.get(i));return q;}
        int playedCount(){int c=0;for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0)c++;return c;}
        int totalGoals(){int g=0;for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0)g+=m.homeGoals+m.awayGoals;return g;}
        String avgGoals(){return playedCount()==0?"0.00":String.format(Locale.US,"%.2f",totalGoals()*1.0/playedCount());}
        int progressPercent(){return (int)Math.round(playedCount()*100.0/matches.size());}
        String bestAttack(){Map<String,Integer> gf=new HashMap<>();for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0){gf.put(m.home,gf.getOrDefault(m.home,0)+m.homeGoals);gf.put(m.away,gf.getOrDefault(m.away,0)+m.awayGoals);}String best="TBD";int max=-1;for(String t:gf.keySet())if(gf.get(t)>max){max=gf.get(t);best=t;}return best+" ("+Math.max(0,max)+")";}
        String bestGroup(){Map<String,Integer> goals=new HashMap<>();for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0)goals.put(m.group,goals.getOrDefault(m.group,0)+m.homeGoals+m.awayGoals);String best="TBD";int max=-1;for(String g:goals.keySet())if(goals.get(g)>max){max=goals.get(g);best=g;}return best+" ("+Math.max(0,max)+" goals)";}
        Match nextFor(String team){for(Match m:matches)if((m.home.equals(team)||m.away.equals(team))&&m.homeGoals<0)return m;return null;}
    }
}
