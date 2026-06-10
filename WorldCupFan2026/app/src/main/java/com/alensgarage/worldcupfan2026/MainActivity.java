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
    final int RED = Color.rgb(210, 0, 45);
    final int RED_DARK = Color.rgb(120, 0, 25);
    final int GOLD = Color.rgb(245, 190, 45);
    final int GREEN = Color.rgb(0, 150, 90);
    int bg, cardBg, text, subText, navBg;

    LinearLayout root, content, bottom;
    TextView title, subtitle;
    Data data;
    Handler handler = new Handler();
    SharedPreferences prefs;
    boolean darkMode;
    String screen = "Home";
    String myTeam = "Croatia";

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
        bg = darkMode ? Color.rgb(8,10,16) : Color.rgb(246,247,249);
        cardBg = darkMode ? Color.rgb(27,29,38) : Color.WHITE;
        text = darkMode ? Color.WHITE : Color.rgb(25,25,32);
        subText = darkMode ? Color.rgb(200,202,212) : Color.rgb(85,85,95);
        navBg = darkMode ? Color.rgb(18,20,28) : Color.WHITE;
    }

    void build() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);
        setContentView(root);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setPadding(dp(20), dp(22), dp(20), dp(14));
        header.setBackground(makeGradient(RED_DARK, RED));
        root.addView(header, new LinearLayout.LayoutParams(-1, dp(134)));

        title = label("World Cup Fan 2026", 27, Color.WHITE, true);
        subtitle = label("v4.0 • Pro UI • Flags • Predictor • Offline Simulator", 14, Color.WHITE, false);
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

    void rebuild() {
        applyTheme();
        build();
        if (screen.equals("Home")) showHome();
        else if (screen.equals("Matches")) showMatches("");
        else if (screen.equals("Groups")) showGroups();
        else if (screen.equals("Predict")) showPredictor();
        else showMore();
    }

    void nav(String icon, String name) {
        TextView b = label(icon + "\n" + name, 12, text, true);
        b.setGravity(Gravity.CENTER);
        b.setBackground(round(darkMode ? Color.rgb(31,35,48) : Color.rgb(238,240,245), dp(18), 0, 0));
        b.setOnClickListener(v -> {
            if (name.equals("Home")) showHome();
            else if (name.equals("Matches")) showMatches("");
            else if (name.equals("Groups")) showGroups();
            else if (name.equals("Predict")) showPredictor();
            else showMore();
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -1, 1);
        lp.setMargins(dp(4), 0, dp(4), 0);
        bottom.addView(b, lp);
    }

    TextView label(String s, int sp, int c, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(sp);
        t.setTextColor(c);
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
        b.setBackground(makeGradient(RED_DARK, RED));
        return b;
    }

    LinearLayout card() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(16), dp(16), dp(16), dp(16));
        l.setBackground(round(cardBg, dp(22), darkMode ? Color.rgb(55,58,72) : Color.rgb(228,228,232), 2));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 0, 0, dp(12));
        content.addView(l, lp);
        return l;
    }

    LinearLayout row() {
        LinearLayout r = new LinearLayout(this);
        r.setOrientation(LinearLayout.HORIZONTAL);
        r.setGravity(Gravity.CENTER_VERTICAL);
        return r;
    }

    void clear(String h, String s) {
        screen = h.equals("World Cup Fan 2026") ? "Home" : (h.equals("All Matches") ? "Matches" : (h.equals("Groups") ? "Groups" : (h.equals("Predictor") ? "Predict" : "More")));
        title.setText(h);
        subtitle.setText(s);
        content.removeAllViews();
    }

    void showHome() {
        clear("World Cup Fan 2026", "v4.0 • Professional fan companion • Offline & fast");

        LinearLayout hero = card();
        hero.addView(label("🔥 WORLD CLASS BUILD v4.0", 22, RED, true));
        hero.addView(label("New professional navigation, team flags, pro cards, match search, premium screen and better predictor flow.", 15, subText, false));

        LinearLayout c = card();
        c.addView(label("⚽ Kick-off countdown", 20, text, true));
        TextView count = label("", 34, RED, true);
        c.addView(count);
        Runnable r = new Runnable() {
            public void run() {
                String d = daysTo("2026-06-11 21:00");
                count.setText(d + " " + (d.equals("1") ? "day" : "days") + " to opening");
                handler.postDelayed(this, 60000);
            }
        };
        r.run();

        LinearLayout c2 = card();
        c2.addView(label(flag(myTeam) + " My Team", 22, text, true));
        c2.addView(label(myTeam + " • next matches, group table and path to the final.", 16, darkMode ? Color.rgb(255,210,215) : RED_DARK, false));
        Button team = btn("Open My Team");
        team.setOnClickListener(v -> showMyTeam());
        c2.addView(team);

        LinearLayout c3 = card();
        c3.addView(label("🔮 Premium Predictor", 22, text, true));
        c3.addView(label("Enter scores, calculate groups, rank third-placed teams and build your knockout path.", 16, subText, false));
        Button p = btn("Start Predictor");
        p.setOnClickListener(v -> showPredictor());
        c3.addView(p);

        LinearLayout c4 = card();
        c4.addView(label("🏆 Dream Final", 22, text, true));
        c4.addView(label(flag(myTeam) + " " + myTeam + " vs Brazil 🇧🇷", 18, GOLD, true));
        Button d = btn("Create Dream Final");
        d.setOnClickListener(v -> showDreamFinal());
        c4.addView(d);
    }

    String daysTo(String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(date);
            long diff = d.getTime() - System.currentTimeMillis();
            return String.valueOf(Math.max(0, diff / 86400000));
        } catch(Exception e) { return "0"; }
    }

    void showMatches(String filter) {
        clear("All Matches", "Search teams, enter scores and save predictions");

        LinearLayout searchCard = card();
        searchCard.addView(label("🔎 Match Search", 18, text, true));
        LinearLayout sr = row();
        EditText q = new EditText(this);
        q.setText(filter);
        q.setHint("Croatia, Brazil, Group A...");
        q.setTextColor(text);
        q.setHintTextColor(subText);
        q.setSingleLine(true);
        q.setBackgroundColor(Color.TRANSPARENT);
        Button go = btn("Search");
        go.setOnClickListener(v -> showMatches(q.getText().toString()));
        sr.addView(q, new LinearLayout.LayoutParams(0, dp(56), 1));
        sr.addView(go, new LinearLayout.LayoutParams(dp(110), dp(56)));
        searchCard.addView(sr);

        int shown = 0;
        String f = filter == null ? "" : filter.toLowerCase(Locale.US).trim();
        for (Match m : data.matches) {
            String hay = (m.home + " " + m.away + " group " + m.group + " " + m.venue).toLowerCase(Locale.US);
            if (f.length() > 0 && !hay.contains(f)) continue;
            shown++;
            LinearLayout c = card();
            c.addView(label(flag(m.home) + " " + m.label() + " " + flag(m.away), 17, text, true));
            c.addView(label(m.date + " • " + m.venue + " • Group " + m.group, 13, subText, false));

            LinearLayout score = row();
            EditText h = scoreBox(m.homeGoals);
            EditText a = scoreBox(m.awayGoals);
            Button save = btn("Save");
            save.setOnClickListener(v -> {
                m.homeGoals = getInt(h);
                m.awayGoals = getInt(a);
                data.save();
                toast("Score saved");
                hideKeyboard(h);
            });
            score.addView(h, new LinearLayout.LayoutParams(0, dp(52), 1));
            score.addView(a, new LinearLayout.LayoutParams(0, dp(52), 1));
            score.addView(save, new LinearLayout.LayoutParams(dp(92), dp(52)));
            c.addView(score);
        }
        if (shown == 0) {
            LinearLayout none = card();
            none.addView(label("No matches found", 20, text, true));
            none.addView(label("Try another team, group or city.", 15, subText, false));
        }
    }

    EditText scoreBox(int val) {
        EditText e = new EditText(this);
        e.setHint("-");
        e.setText(val >= 0 ? "" + val : "");
        e.setTextSize(20);
        e.setInputType(InputType.TYPE_CLASS_NUMBER);
        e.setGravity(Gravity.CENTER);
        e.setTextColor(text);
        e.setHintTextColor(subText);
        return e;
    }

    int getInt(EditText e) {
        try { return Integer.parseInt(e.getText().toString()); }
        catch(Exception x) { return -1; }
    }

    void showGroups() {
        clear("Groups", "Live table calculation from your scores");

        Map<String, List<TeamRow>> map = data.standings();
        for (String g : data.groups) {
            LinearLayout c = card();
            c.addView(label("Group " + g, 22, RED, true));
            List<TeamRow> rows = map.get(g);
            for (int i=0; i<rows.size(); i++) {
                TeamRow r = rows.get(i);
                String medal = i == 0 ? "🥇" : (i == 1 ? "🥈" : (i == 2 ? "🥉" : "•"));
                c.addView(label(medal + " " + flag(r.team) + " " + r.team + "   " + r.pts + " pts   " + r.gf + ":" + r.ga + "   GD " + (r.gf-r.ga), 15, text, false));
            }
        }

        LinearLayout third = card();
        third.addView(label("🥉 Best third-placed teams", 21, text, true));
        List<TeamRow> thirds = data.bestThirds();
        for (int i=0; i<thirds.size(); i++) {
            TeamRow r = thirds.get(i);
            third.addView(label((i+1) + ". " + flag(r.team) + " " + r.team + " • Group " + r.group + " • " + r.pts + " pts", 14, i < 8 ? GREEN : subText, false));
        }
    }

    void showPredictor() {
        clear("Predictor", "Build your tournament path");

        LinearLayout c = card();
        c.addView(label("🔮 Your Tournament Simulator", 24, text, true));
        c.addView(label("1. Enter scores in Matches.\n2. Groups update automatically.\n3. Top 2 + best 8 third-placed teams qualify.\n4. Knockout path is generated.\n5. Share your prediction.", 15, subText, false));

        Button scores = btn("Enter Match Scores");
        scores.setOnClickListener(v -> showMatches(""));
        c.addView(scores);

        Button bracket = btn("View Knockout Bracket");
        bracket.setOnClickListener(v -> showKnockout());
        c.addView(bracket);

        Button share = btn("Share My Prediction");
        share.setOnClickListener(v -> sharePrediction());
        c.addView(share);

        Button dream = btn("Dream Final");
        dream.setOnClickListener(v -> showDreamFinal());
        c.addView(dream);

        Button reset = btn("Reset Scores");
        reset.setOnClickListener(v -> { data.reset(); showPredictor(); });
        c.addView(reset);

        LinearLayout q = card();
        q.addView(label("✅ Current qualified teams", 20, text, true));
        List<TeamRow> qual = data.qualified();
        for (int i=0; i<qual.size(); i++) {
            TeamRow r = qual.get(i);
            q.addView(label((i+1) + ". " + flag(r.team) + " " + r.team + " • Group " + r.group + " • " + r.pts + " pts", 13, subText, false));
        }
    }

    void showKnockout() {
        clear("Knockout Bracket", "Round of 32 to Final");

        List<TeamRow> q = data.qualified();
        String[] rounds = {"Round of 32", "Round of 16", "Quarter-finals", "Semi-finals", "Final"};
        int games = 16;
        for (String r : rounds) {
            LinearLayout c = card();
            c.addView(label("🏆 " + r, 22, RED, true));
            for (int i=0; i<games; i++) {
                int aIndex = (i*2) % Math.max(1, q.size());
                int bIndex = (i*2+1) % Math.max(1, q.size());
                String a = q.size() > 0 ? q.get(aIndex).team : "TBD";
                String b = q.size() > 1 ? q.get(bIndex).team : "TBD";
                c.addView(label("   " + flag(a) + " " + a + "\n      ├─ vs\n   " + flag(b) + " " + b, 15, text, false));
            }
            games = Math.max(1, games / 2);
        }
    }

    void showDreamFinal() {
        clear("Dream Final", "Create a shareable fan prediction");

        LinearLayout c = card();
        c.addView(label("🏆 Dream Final Builder", 24, text, true));
        c.addView(label("Pick your champion story and share it with friends.", 15, subText, false));

        Spinner left = spinner(data.teams, myTeam);
        Spinner right = spinner(data.teams, "Brazil");
        c.addView(label("Team 1", 14, subText, false));
        c.addView(left);
        c.addView(label("Team 2", 14, subText, false));
        c.addView(right);

        Button share = btn("Share Dream Final");
        share.setOnClickListener(v -> {
            String a = left.getSelectedItem().toString();
            String b = right.getSelectedItem().toString();
            shareText("🏆 My dream World Cup 2026 final:\n\n" + flag(a) + " " + a + " vs " + flag(b) + " " + b + "\n\nMade with World Cup Fan 2026");
        });
        c.addView(share);
    }

    Spinner spinner(ArrayList<String> items, String selected) {
        Spinner sp = new Spinner(this);
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        sp.setAdapter(ad);
        int pos = items.indexOf(selected);
        if (pos >= 0) sp.setSelection(pos);
        return sp;
    }

    void showMyTeam() {
        clear("My Team", "Personalized fan hub");

        LinearLayout c = card();
        c.addView(label(flag(myTeam) + " Selected team: " + myTeam, 23, RED, true));

        Spinner sp = spinner(data.teams, myTeam);
        c.addView(sp);

        Button save = btn("Save My Team");
        save.setOnClickListener(v -> {
            myTeam = sp.getSelectedItem().toString();
            prefs.edit().putString("team", myTeam).apply();
            toast("My Team saved");
            showMyTeam();
        });
        c.addView(save);

        LinearLayout m = card();
        m.addView(label("⚽ Matches", 20, text, true));
        for (Match ma : data.matches) {
            if (ma.home.equals(myTeam) || ma.away.equals(myTeam)) {
                m.addView(label(ma.date + " • " + flag(ma.home) + " " + ma.home + " vs " + flag(ma.away) + " " + ma.away, 14, subText, false));
            }
        }

        LinearLayout notes = card();
        notes.addView(label("⭐ Fan Notes", 20, text, true));
        notes.addView(label("Squads and final player lists can be added after official tournament lists are published. No official logos or player photos are used.", 14, subText, false));
    }

    void showMore() {
        clear("More", "Settings, premium and legal safety");

        LinearLayout c = card();

        Button team = btn("My Team");
        team.setOnClickListener(v -> showMyTeam());
        c.addView(team);

        Button stats = btn("Statistics");
        stats.setOnClickListener(v -> showStats());
        c.addView(stats);

        Button knockout = btn("Knockout");
        knockout.setOnClickListener(v -> showKnockout());
        c.addView(knockout);

        Button mode = btn(darkMode ? "Switch to Light Mode" : "Switch to Dark Mode");
        mode.setOnClickListener(v -> {
            darkMode = !darkMode;
            prefs.edit().putBoolean("darkMode", darkMode).apply();
            rebuild();
        });
        c.addView(mode);

        Button premium = btn("Premium / Pro Version");
        premium.setOnClickListener(v -> showPremium());
        c.addView(premium);

        LinearLayout l = card();
        l.addView(label("Version 4.0", 22, RED, false));
        l.addView(label("Independent fan-made app. No official FIFA logo, no official crests, no player photos, no live streaming. Works offline and lets fans build predictions.", 15, subText, false));
    }

    void showPremium() {
        clear("Premium", "Unlock the full fan simulator");

        LinearLayout c = card();
        c.addView(label("💎 Premium Version", 25, GOLD, true));
        c.addView(label("Suggested price: €1.49 - €2.99\n\nPremium ideas:\n• Multiple saved predictions\n• Share prediction image\n• Export PDF\n• Advanced statistics\n• Custom tournaments\n• Premium themes\n• Home screen widget\n• Match reminders", 16, subText, false));

        Button share = btn("Share App Idea");
        share.setOnClickListener(v -> shareText("I'm using World Cup Fan 2026 - offline predictor, groups and knockout simulator."));
        c.addView(share);
    }

    void showStats() {
        clear("Statistics", "Calculated from your entered scores");

        int played=0, goals=0;
        Map<String,Integer> gf = new HashMap<>();
        for (Match m : data.matches) {
            if (m.homeGoals >= 0 && m.awayGoals >= 0) {
                played++;
                goals += m.homeGoals + m.awayGoals;
                gf.put(m.home, gf.getOrDefault(m.home, 0) + m.homeGoals);
                gf.put(m.away, gf.getOrDefault(m.away, 0) + m.awayGoals);
            }
        }
        String best="TBD"; int max=-1;
        for (String t : gf.keySet()) if (gf.get(t) > max) { max = gf.get(t); best = t; }

        LinearLayout c = card();
        c.addView(label("📊 Tournament Statistics", 24, text, true));
        c.addView(label("Played matches: " + played, 18, subText, false));
        c.addView(label("Goals: " + goals, 18, subText, false));
        c.addView(label("Best attack: " + flag(best) + " " + best + " (" + Math.max(0, max) + ")", 18, RED, true));
        c.addView(label("Average goals: " + (played == 0 ? "0.0" : String.format(Locale.US, "%.2f", (goals * 1.0 / played))), 18, subText, false));
    }

    void sharePrediction() {
        StringBuilder msg = new StringBuilder();
        msg.append("⚽ My World Cup Fan 2026 prediction\n\n");
        msg.append("My team: ").append(flag(myTeam)).append(" ").append(myTeam).append("\n\nQualified teams:");
        List<TeamRow> q = data.qualified();
        for (int i=0; i<Math.min(12, q.size()); i++) msg.append("\n").append(i+1).append(". ").append(flag(q.get(i).team)).append(" ").append(q.get(i).team);
        msg.append("\n\nMade with World Cup Fan 2026");
        shareText(msg.toString());
    }

    void shareText(String msg) {
        Intent send = new Intent(Intent.ACTION_SEND);
        send.setType("text/plain");
        send.putExtra(Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(send, "Share"));
    }

    void hideKeyboard(View v) {
        try { ((android.view.inputmethod.InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0); } catch(Exception e) {}
    }

    void toast(String s) { Toast.makeText(this, s, Toast.LENGTH_SHORT).show(); }

    String flag(String t) {
        if (t == null) return "🏳";
        if (t.equals("Croatia")) return "🇭🇷";
        if (t.equals("Brazil")) return "🇧🇷";
        if (t.equals("Germany")) return "🇩🇪";
        if (t.equals("Argentina")) return "🇦🇷";
        if (t.equals("France")) return "🇫🇷";
        if (t.equals("Spain")) return "🇪🇸";
        if (t.equals("Portugal")) return "🇵🇹";
        if (t.equals("England")) return "🏴";
        if (t.equals("Netherlands")) return "🇳🇱";
        if (t.equals("Belgium")) return "🇧🇪";
        if (t.equals("Mexico")) return "🇲🇽";
        if (t.equals("USA")) return "🇺🇸";
        if (t.equals("Canada")) return "🇨🇦";
        if (t.equals("Japan")) return "🇯🇵";
        if (t.equals("Korea Republic")) return "🇰🇷";
        if (t.equals("Morocco")) return "🇲🇦";
        if (t.equals("Uruguay")) return "🇺🇾";
        if (t.equals("Switzerland")) return "🇨🇭";
        if (t.equals("Denmark")) return "🇩🇰";
        if (t.equals("Australia")) return "🇦🇺";
        if (t.equals("South Africa")) return "🇿🇦";
        if (t.equals("Qatar")) return "🇶🇦";
        if (t.equals("Scotland")) return "🏴";
        if (t.equals("Paraguay")) return "🇵🇾";
        if (t.equals("Ecuador")) return "🇪🇨";
        if (t.equals("Ivory Coast")) return "🇨🇮";
        if (t.equals("Tunisia")) return "🇹🇳";
        if (t.equals("Egypt")) return "🇪🇬";
        if (t.equals("Norway")) return "🇳🇴";
        if (t.equals("Saudi Arabia")) return "🇸🇦";
        if (t.equals("Senegal")) return "🇸🇳";
        if (t.equals("Iran")) return "🇮🇷";
        if (t.equals("Austria")) return "🇦🇹";
        if (t.equals("Algeria")) return "🇩🇿";
        if (t.equals("Colombia")) return "🇨🇴";
        if (t.equals("Ghana")) return "🇬🇭";
        if (t.equals("Panama")) return "🇵🇦";
        return "🏳";
    }

    Drawable makeGradient(int a, int b) {
        GradientDrawable g = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{a, b});
        return g;
    }

    Drawable round(int color, int radius, int strokeColor, int strokeWidth) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(radius);
        if (strokeWidth > 0) g.setStroke(strokeWidth, strokeColor);
        return g;
    }

    int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }

    static class Match {
        String group, home, away, date, venue;
        int homeGoals=-1, awayGoals=-1;
        Match(String g, String h, String a, String d, String v) { group=g; home=h; away=a; date=d; venue=v; }
        String label() { return home + "  " + (homeGoals>=0?homeGoals:"-") + " : " + (awayGoals>=0?awayGoals:"-") + "  " + away; }
    }

    static class TeamRow {
        String group, team;
        int pts,gf,ga,w,d,l;
        TeamRow(String g, String t) { group=g; team=t; }
    }

    class Data {
        String[] groups = {"A","B","C","D","E","F","G","H","I","J","K","L"};
        ArrayList<String> teams = new ArrayList<>();
        ArrayList<Match> matches = new ArrayList<>();
        SharedPreferences sp;

        Data(Context c) {
            sp = c.getSharedPreferences("scores", 0);
            seed();
            load();
        }

        void seed() {
            String[][] g = {
                {"Mexico","South Africa","Croatia","Korea Republic"},
                {"Canada","Switzerland","Qatar","TBD B4"},
                {"Brazil","Morocco","Scotland","TBD C4"},
                {"USA","Australia","Paraguay","TBD D4"},
                {"Germany","Ecuador","Ivory Coast","TBD E4"},
                {"Netherlands","Japan","Tunisia","TBD F4"},
                {"Belgium","Egypt","Norway","TBD G4"},
                {"Spain","Uruguay","Saudi Arabia","TBD H4"},
                {"France","Senegal","Iran","TBD I4"},
                {"Argentina","Austria","Algeria","TBD J4"},
                {"England","Colombia","Ghana","TBD K4"},
                {"Portugal","Denmark","Panama","TBD L4"}
            };
            String[] venues = {"Mexico City","Toronto","Los Angeles","New York/New Jersey","Dallas","Miami","Vancouver","Atlanta","Houston","Kansas City","Boston","Seattle"};
            int day=11;
            for (int x=0; x<g.length; x++) {
                String gr = groups[x];
                for (String t : g[x]) teams.add(t);
                String[] t = g[x];
                matches.add(new Match(gr,t[0],t[1],"2026-06-"+(day+x%15),venues[x]));
                matches.add(new Match(gr,t[2],t[3],"2026-06-"+(day+1+x%15),venues[(x+3)%venues.length]));
                matches.add(new Match(gr,t[0],t[2],"2026-06-"+(day+6+x%12),venues[(x+6)%venues.length]));
                matches.add(new Match(gr,t[1],t[3],"2026-06-"+(day+7+x%12),venues[(x+8)%venues.length]));
                matches.add(new Match(gr,t[0],t[3],"2026-06-"+(day+12+x%7),venues[(x+9)%venues.length]));
                matches.add(new Match(gr,t[1],t[2],"2026-06-"+(day+12+x%7),venues[(x+10)%venues.length]));
            }
            Collections.sort(teams);
        }

        void load() {
            for (int i=0; i<matches.size(); i++) {
                matches.get(i).homeGoals = sp.getInt("h"+i, -1);
                matches.get(i).awayGoals = sp.getInt("a"+i, -1);
            }
        }

        void save() {
            SharedPreferences.Editor e = sp.edit();
            for (int i=0; i<matches.size(); i++) {
                e.putInt("h"+i, matches.get(i).homeGoals);
                e.putInt("a"+i, matches.get(i).awayGoals);
            }
            e.apply();
        }

        void reset() {
            sp.edit().clear().apply();
            for (Match m : matches) { m.homeGoals=-1; m.awayGoals=-1; }
        }

        Map<String,List<TeamRow>> standings() {
            Map<String,List<TeamRow>> out = new LinkedHashMap<>();
            for (String gr : groups) {
                Map<String,TeamRow> m = new LinkedHashMap<>();
                for (Match ma : matches) if (ma.group.equals(gr)) {
                    m.putIfAbsent(ma.home, new TeamRow(gr, ma.home));
                    m.putIfAbsent(ma.away, new TeamRow(gr, ma.away));
                    if (ma.homeGoals>=0 && ma.awayGoals>=0) {
                        TeamRow h=m.get(ma.home), a=m.get(ma.away);
                        h.gf+=ma.homeGoals; h.ga+=ma.awayGoals;
                        a.gf+=ma.awayGoals; a.ga+=ma.homeGoals;
                        if (ma.homeGoals>ma.awayGoals) { h.w++; h.pts+=3; a.l++; }
                        else if (ma.homeGoals<ma.awayGoals) { a.w++; a.pts+=3; h.l++; }
                        else { h.d++; a.d++; h.pts++; a.pts++; }
                    }
                }
                ArrayList<TeamRow> list = new ArrayList<>(m.values());
                Collections.sort(list, (x,y) -> y.pts!=x.pts ? y.pts-x.pts : ((y.gf-y.ga)!=(x.gf-x.ga) ? (y.gf-y.ga)-(x.gf-x.ga) : y.gf-x.gf));
                out.put(gr, list);
            }
            return out;
        }

        List<TeamRow> bestThirds() {
            ArrayList<TeamRow> third = new ArrayList<>();
            for (List<TeamRow> l : standings().values()) if (l.size()>2) third.add(l.get(2));
            Collections.sort(third, (x,y) -> y.pts!=x.pts ? y.pts-x.pts : ((y.gf-y.ga)!=(x.gf-x.ga) ? (y.gf-y.ga)-(x.gf-x.ga) : y.gf-x.gf));
            return third;
        }

        List<TeamRow> qualified() {
            ArrayList<TeamRow> q = new ArrayList<>();
            for (List<TeamRow> l : standings().values()) {
                if (l.size()>0) q.add(l.get(0));
                if (l.size()>1) q.add(l.get(1));
            }
            List<TeamRow> th = bestThirds();
            for (int i=0; i<Math.min(8, th.size()); i++) q.add(th.get(i));
            return q;
        }
    }
}
