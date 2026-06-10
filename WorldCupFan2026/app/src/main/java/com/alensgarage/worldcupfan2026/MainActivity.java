package com.alensgarage.worldcupfan2026;

import android.app.*;
import android.os.*;
import android.content.*;
import android.content.SharedPreferences;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import java.text.*;
import java.util.*;

public class MainActivity extends Activity {
    final int RED=Color.rgb(220,0,48), RED_DARK=Color.rgb(92,0,20), GOLD=Color.rgb(248,197,55), GREEN=Color.rgb(0,158,96), BLUE=Color.rgb(35,102,235);
    int bg, cardBg, text, subText, navBg, chipBg, stroke;
    LinearLayout root, content, bottom;
    TextView title, subtitle;
    SharedPreferences prefs;
    Data data;
    Handler handler=new Handler();
    boolean darkMode;
    String lang="EN", currentScreen="Home", myTeam="Croatia", matchFilter="", groupFilter="All";

    public void onCreate(Bundle b){
        super.onCreate(b);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        prefs=getSharedPreferences("worldcupfan2026",0);
        darkMode=prefs.getBoolean("darkMode",true);
        lang=prefs.getString("lang","EN");
        myTeam=prefs.getString("team","Croatia");
        data=new Data(this);
        applyTheme();
        build();
        showHome();
    }

    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) hideSystemBars();
    }

    void hideSystemBars(){
        try{
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN|
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }catch(Exception e){}
    }

    void applyTheme(){
        bg=darkMode?Color.rgb(6,8,13):Color.rgb(245,246,249);
        cardBg=darkMode?Color.rgb(22,25,35):Color.WHITE;
        text=darkMode?Color.WHITE:Color.rgb(22,24,32);
        subText=darkMode?Color.rgb(198,202,214):Color.rgb(83,86,96);
        navBg=darkMode?Color.rgb(13,16,23):Color.WHITE;
        chipBg=darkMode?Color.rgb(34,38,52):Color.rgb(238,240,245);
        stroke=darkMode?Color.rgb(58,62,78):Color.rgb(224,226,232);
    }

    String tr(String key){
        String en=key, hr=key, de=key, es=key, fr=key;
        if(key.equals("Home")){hr="Početna";de="Start";es="Inicio";fr="Accueil";}
        else if(key.equals("Matches")){hr="Utakmice";de="Spiele";es="Partidos";fr="Matchs";}
        else if(key.equals("Groups")){hr="Skupine";de="Gruppen";es="Grupos";fr="Groupes";}
        else if(key.equals("Predict")){hr="Prognoza";de="Tipp";es="Predicción";fr="Prono";}
        else if(key.equals("More")){hr="Više";de="Mehr";es="Más";fr="Plus";}
        else if(key.equals("My Team")){hr="Moja reprezentacija";de="Mein Team";es="Mi equipo";fr="Mon équipe";}
        else if(key.equals("Start Simulator")){hr="Pokreni simulator";de="Simulator starten";es="Iniciar simulador";fr="Démarrer";}
        else if(key.equals("Team Hub")){hr="Centar reprezentacije";de="Team-Zentrale";es="Centro equipo";fr="Hub équipe";}
        else if(key.equals("Share Poster")){hr="Podijeli poster";de="Poster teilen";es="Compartir póster";fr="Partager poster";}
        else if(key.equals("Smart Match Center")){hr="Pametni centar utakmica";de="Match-Zentrale";es="Centro de partidos";fr="Centre matchs";}
        else if(key.equals("Search team, group or host city")){hr="Traži reprezentaciju, skupinu ili grad";de="Team, Gruppe oder Stadt suchen";es="Buscar equipo, grupo o ciudad";fr="Chercher équipe, groupe ou ville";}
        else if(key.equals("Apply Search")){hr="Primijeni pretragu";de="Suchen";es="Buscar";fr="Rechercher";}
        else if(key.equals("Save Result")){hr="Spremi rezultat";de="Ergebnis speichern";es="Guardar resultado";fr="Enregistrer";}
        else if(key.equals("Prediction Studio")){hr="Studio prognoze";de="Prognose-Studio";es="Estudio de predicción";fr="Studio prédiction";}
        else if(key.equals("Enter Scores")){hr="Upiši rezultate";de="Ergebnisse eingeben";es="Introducir marcadores";fr="Saisir scores";}
        else if(key.equals("Pro Bracket")){hr="Pro kostur";de="Pro-Turnierbaum";es="Cuadro Pro";fr="Tableau Pro";}
        else if(key.equals("Dream Final")){hr="Finale snova";de="Traumfinale";es="Final soñado";fr="Finale rêvée";}
        else if(key.equals("Reset Scores")){hr="Resetiraj rezultate";de="Zurücksetzen";es="Reiniciar";fr="Réinitialiser";}
        else if(key.equals("Host Cities")){hr="Gradovi domaćini";de="Austragungsorte";es="Ciudades sede";fr="Villes hôtes";}
        else if(key.equals("Statistics")){hr="Statistika";de="Statistiken";es="Estadísticas";fr="Statistiques";}
        else if(key.equals("Premium")){hr="Premium";de="Premium";es="Premium";fr="Premium";}
        else if(key.equals("Switch to Light Mode")){hr="Prebaci na svijetli način";de="Hellmodus";es="Modo claro";fr="Mode clair";}
        else if(key.equals("Switch to Dark Mode")){hr="Prebaci na tamni način";de="Dunkelmodus";es="Modo oscuro";fr="Mode sombre";}
        else if(key.equals("Language")){hr="Jezik";de="Sprache";es="Idioma";fr="Langue";}
        else if(key.equals(myTeam + " Road to Final")){hr="Hrvatska put do finala";de="Kroatien Weg ins Finale";es="Croacia camino a la final";fr="Croatie route finale";}
        else if(key.equals("Free vs Pro")){hr="Besplatno vs Pro";de="Gratis vs Pro";es="Gratis vs Pro";fr="Gratuit vs Pro";}
        else if(key.equals("Open")){hr="Otvori";de="Öffnen";es="Abrir";fr="Ouvrir";}
        if(lang.equals("HR")) return hr; if(lang.equals("DE")) return de; if(lang.equals("ES")) return es; if(lang.equals("FR")) return fr; return en;
    }

    void build(){
        root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);
        setContentView(root);
        hideSystemBars();

        LinearLayout header=new LinearLayout(this);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setPadding(dp(20),dp(18),dp(20),dp(10));
        header.setBackground(gradient(RED_DARK,RED,0));
        root.addView(header,new LinearLayout.LayoutParams(-1,dp(124)));
        title=label("World Cup Fan 2026",27,Color.WHITE,true);
        subtitle=label("v11.0 Play Store Polish Edition • 5 languages • Immersive fullscreen",14,Color.WHITE,false);
        header.addView(title); header.addView(subtitle);

        ScrollView sv=new ScrollView(this);
        content=new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(12),dp(12),dp(12),dp(92));
        sv.addView(content);
        root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));

        bottom=new LinearLayout(this);
        bottom.setOrientation(LinearLayout.HORIZONTAL);
        bottom.setPadding(dp(7),dp(6),dp(7),dp(6));
        bottom.setBackgroundColor(navBg);
        root.addView(bottom,new LinearLayout.LayoutParams(-1,dp(72)));
        nav("🏠","Home"); nav("⚽","Matches"); nav("🏆","Groups"); nav("🔮","Predict"); nav("☰","More");
    }

    void nav(String icon,String name){
        boolean selected=currentScreen.equals(name)||(currentScreen.equals("Predict")&&name.equals("Predict"));
        TextView b=label(icon+"\n"+tr(name),11,selected?Color.WHITE:text,true);
        b.setGravity(Gravity.CENTER);
        b.setBackground(selected?gradient(RED_DARK,RED,dp(18)):round(chipBg,dp(18),0));
        b.setOnClickListener(v->{ if(name.equals("Home"))showHome(); else if(name.equals("Matches"))showMatches(); else if(name.equals("Groups"))showGroups(); else if(name.equals("Predict"))showPredictor(); else showMore(); });
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-1,1); lp.setMargins(dp(4),0,dp(4),0);
        bottom.addView(b,lp);
    }

    void redraw(){applyTheme();build(); if(currentScreen.equals("Home"))showHome(); else if(currentScreen.equals("Matches"))showMatches(); else if(currentScreen.equals("Groups"))showGroups(); else if(currentScreen.equals("Predict"))showPredictor(); else showMore();}

    TextView label(String s,int sp,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(sp);t.setTextColor(color);t.setPadding(dp(4),dp(3),dp(4),dp(3));if(bold)t.setTypeface(Typeface.DEFAULT_BOLD);return t;}
    Button btn(String s){Button b=new Button(this);b.setText(s);b.setTextSize(14);b.setTextColor(Color.WHITE);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT_BOLD);b.setBackground(gradient(RED_DARK,RED,dp(18)));return b;}
    Button outline(String s){Button b=new Button(this);b.setText(s);b.setTextSize(13);b.setTextColor(text);b.setAllCaps(false);b.setTypeface(Typeface.DEFAULT_BOLD);b.setBackground(round(chipBg,dp(18),1));return b;}
    TextView chip(String s){TextView t=label(s,13,text,true);t.setGravity(Gravity.CENTER);t.setPadding(dp(10),dp(8),dp(10),dp(8));t.setBackground(round(chipBg,dp(18),1));return t;}
    LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(16),dp(16),dp(16),dp(16));l.setBackground(round(cardBg,dp(24),1));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,0,0,dp(12));content.addView(l,lp);return l;}
    LinearLayout hrow(){LinearLayout r=new LinearLayout(this);r.setOrientation(LinearLayout.HORIZONTAL);r.setGravity(Gravity.CENTER_VERTICAL);return r;}
    void clear(String h,String s,String key){currentScreen=key;title.setText(h);subtitle.setText(s);content.removeAllViews();hideSystemBars();}

    void showHome(){
        clear("World Cup Fan 2026","v11.0 • "+lang+" • Global fan app","Home");
        
        LinearLayout upgrade = card();
        upgrade.addView(label("🚀 v11 Pro Upgrades", 22, text, true));
        upgrade.addView(label("New: visual bracket, Golden Boot, MVP, team compare, backup export, quiz, stadium guide, city guide and achievements.", 14, subText, false));
LinearLayout hero=card(); hero.setBackground(gradient(RED_DARK,RED,dp(24)));
        hero.addView(label("🌍 GLOBAL EDITION v11.0",24,Color.WHITE,true));
        hero.addView(label("Immersive fullscreen, 5 languages, Croatia hub, clean predictor, group tables and share-ready poster.",15,Color.WHITE,false));
        hero.addView(kpiRow("Progress",data.progressPercent()+"%","Played",""+data.playedCount(),"Goals",""+data.totalGoals(),true));

        LinearLayout countCard=card(); countCard.addView(label("⚽ Kick-off countdown",18,subText,true));
        TextView count=label("",34,RED,true); countCard.addView(count);
        Runnable r=new Runnable(){public void run(){String d=daysTo("2026-06-11 21:00"); count.setText(d+" "+(d.equals("1")?"day":"days")+" to opening"); handler.postDelayed(this,60000);}}; r.run();

        LinearLayout cro=card(); cro.addView(label(flag(myTeam)+" "+myTeam+" Road to Final",22,text,true));
        cro.addView(label(groupLineFor(myTeam),15,subText,false));
        cro.addView(pathPreview(myTeam));
        Button hub=btn(tr("Team Hub")); hub.setOnClickListener(v->showMyTeam()); cro.addView(hub);

        LinearLayout studio=card(); studio.addView(label("🔮 "+tr("Prediction Studio"),22,text,true));
        studio.addView(label("Scores → tables → best thirds → bracket → champion → share poster.",15,subText,false));
        Button start=btn(tr("Start Simulator")); start.setOnClickListener(v->showPredictor()); studio.addView(start);
    }

    LinearLayout pathPreview(String team){
        LinearLayout p=new LinearLayout(this);p.setOrientation(LinearLayout.VERTICAL);p.setPadding(0,dp(6),0,dp(8));
        String[] steps={"Group "+groupOfTeam(myTeam),"Round of 32","Round of 16","Quarter-final","Semi-final","Final"};
        for(String s:steps)p.addView(label("   "+flag(team)+" "+team+"  →  "+s,14,subText,false));
        return p;
    }

    LinearLayout kpiRow(String a,String av,String b,String bv,String c,String cv,boolean light){
        LinearLayout r=hrow();r.setPadding(0,dp(10),0,0);
        r.addView(kpi(a,av,light),new LinearLayout.LayoutParams(0,dp(72),1));r.addView(kpi(b,bv,light),new LinearLayout.LayoutParams(0,dp(72),1));r.addView(kpi(c,cv,light),new LinearLayout.LayoutParams(0,dp(72),1));return r;
    }
    LinearLayout kpi(String n,String v,boolean light){LinearLayout k=new LinearLayout(this);k.setOrientation(LinearLayout.VERTICAL);k.setGravity(Gravity.CENTER);k.setPadding(dp(4),dp(4),dp(4),dp(4));k.setBackground(round(light?Color.argb(45,255,255,255):chipBg,dp(18),0));TextView val=label(v,22,light?Color.WHITE:RED,true);val.setGravity(Gravity.CENTER);TextView name=label(n,11,light?Color.WHITE:subText,false);name.setGravity(Gravity.CENTER);k.addView(val);k.addView(name);return k;}
    String daysTo(String date){try{Date d=new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.US).parse(date);long diff=d.getTime()-System.currentTimeMillis();return String.valueOf(Math.max(0,diff/86400000));}catch(Exception e){return "0";}}

    void showMatches(){
        clear(tr("Matches"),tr("Smart Match Center"),"Matches");
        LinearLayout top=card(); top.addView(label("🔎 "+tr("Smart Match Center"),21,text,true));
        EditText search=new EditText(this); search.setHint(tr("Search team, group or host city")); search.setText(matchFilter); search.setSingleLine(true); search.setTextColor(text); search.setHintTextColor(subText); search.setPadding(dp(14),0,dp(14),0); search.setBackground(round(chipBg,dp(16),0)); top.addView(search,new LinearLayout.LayoutParams(-1,dp(54)));
        LinearLayout f1=hrow(); for(String g:new String[]{"All","A","B","C","D","E","F"}) addGroupChip(f1,g,search); top.addView(f1);
        LinearLayout f2=hrow(); for(String g:new String[]{"G","H","I","J","K","L"}) addGroupChip(f2,g,search); top.addView(f2);
        Button go=btn(tr("Apply Search")); go.setOnClickListener(v->{matchFilter=search.getText().toString();showMatches();}); top.addView(go);

        String f=matchFilter.toLowerCase(Locale.US).trim(); int shown=0;
        for(Match m:data.matches){ if(!groupFilter.equals("All")&&!m.group.equals(groupFilter))continue; String hay=(m.group+" "+m.home+" "+m.away+" "+m.venue).toLowerCase(Locale.US); if(f.length()>0&&!hay.contains(f))continue; shown++; matchCard(m);}
        if(shown==0){LinearLayout n=card();n.addView(label("No matches found",21,text,true));n.addView(label("Try another team, group or city.",15,subText,false));}
    }

    void addGroupChip(LinearLayout row,String g,EditText search){TextView ch=chip(g);boolean sel=g.equals(groupFilter);ch.setTextColor(sel?Color.WHITE:text);if(sel)ch.setBackground(gradient(RED_DARK,RED,dp(18)));ch.setOnClickListener(v->{groupFilter=g;matchFilter=search.getText().toString();showMatches();});LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,dp(42),1);lp.setMargins(dp(2),dp(5),dp(2),dp(1));row.addView(ch,lp);}
    void matchCard(Match m){
        LinearLayout c=card();c.addView(label("Group "+m.group+" • "+m.date+" • "+m.venue,12,subText,true));
        LinearLayout teams=hrow();
        TextView left=label(flag(m.home)+"\n"+shortName(m.home),15,text,true);left.setGravity(Gravity.CENTER);
        TextView score=label(display(m.homeGoals)+" : "+display(m.awayGoals),30,RED,true);score.setGravity(Gravity.CENTER);
        TextView right=label(flag(m.away)+"\n"+shortName(m.away),15,text,true);right.setGravity(Gravity.CENTER);
        teams.addView(left,new LinearLayout.LayoutParams(0,dp(80),1));teams.addView(score,new LinearLayout.LayoutParams(0,dp(80),1));teams.addView(right,new LinearLayout.LayoutParams(0,dp(80),1));c.addView(teams);
        LinearLayout controls=hrow(); TextView hs=scoreLabel(m.homeGoals), as=scoreLabel(m.awayGoals);
        Button hm=outline("−");hm.setOnClickListener(v->{m.homeGoals=change(m.homeGoals,-1);hs.setText(display(m.homeGoals));score.setText(display(m.homeGoals)+" : "+display(m.awayGoals));});
        Button hp=outline("+");hp.setOnClickListener(v->{m.homeGoals=change(m.homeGoals,1);hs.setText(display(m.homeGoals));score.setText(display(m.homeGoals)+" : "+display(m.awayGoals));});
        Button am=outline("−");am.setOnClickListener(v->{m.awayGoals=change(m.awayGoals,-1);as.setText(display(m.awayGoals));score.setText(display(m.homeGoals)+" : "+display(m.awayGoals));});
        Button ap=outline("+");ap.setOnClickListener(v->{m.awayGoals=change(m.awayGoals,1);as.setText(display(m.awayGoals));score.setText(display(m.homeGoals)+" : "+display(m.awayGoals));});
        controls.addView(hm,new LinearLayout.LayoutParams(0,dp(48),1));controls.addView(hs,new LinearLayout.LayoutParams(0,dp(48),1));controls.addView(hp,new LinearLayout.LayoutParams(0,dp(48),1));controls.addView(am,new LinearLayout.LayoutParams(0,dp(48),1));controls.addView(as,new LinearLayout.LayoutParams(0,dp(48),1));controls.addView(ap,new LinearLayout.LayoutParams(0,dp(48),1));c.addView(controls);
        Button save=btn(tr("Save Result"));save.setOnClickListener(v->{data.save();toast("Saved");});c.addView(save);
    }
    int change(int v,int d){if(v<0)v=0;v+=d;if(v<0)v=0;return v;} String display(int v){return v>=0?""+v:"-";}
    TextView scoreLabel(int v){TextView t=label(display(v),21,text,true);t.setGravity(Gravity.CENTER);t.setBackground(round(chipBg,dp(14),0));return t;} String shortName(String s){return s.length()>12?s.substring(0,12):s;}

    void showGroups(){
        clear(tr("Groups"),"P/W/D/L/GD/Pts","Groups");
        Map<String,List<TeamRow>> map=data.standings();
        for(String g:data.groups){LinearLayout c=card();c.addView(label("Group "+g,23,RED,true));c.addView(tableRow("#","Team","P","W","D","L","GD","Pts",true,subText));List<TeamRow> rows=map.get(g);for(int i=0;i<rows.size();i++){TeamRow r=rows.get(i);int col=i<2?GREEN:(i==2?GOLD:subText);c.addView(tableRow((i+1)+"",flag(r.team)+" "+r.team,r.p+"",r.w+"",r.d+"",r.l+"",(r.gf-r.ga)+"",r.pts+"",false,col));}}
        LinearLayout third=card();third.addView(label("🥉 Best third-placed teams",22,text,true));List<TeamRow> th=data.bestThirds();for(int i=0;i<th.size();i++){TeamRow r=th.get(i);third.addView(label((i+1)+". "+flag(r.team)+" "+r.team+" • Group "+r.group+" • "+r.pts+" pts",14,i<8?GREEN:subText,false));}
    }
    LinearLayout tableRow(String pos,String team,String p,String w,String d,String l,String gd,String pts,boolean bold,int color){LinearLayout r=hrow();r.setPadding(0,dp(4),0,dp(4));r.addView(cell(pos,.45f,bold,color));r.addView(cell(team,2.4f,bold,color));r.addView(cell(p,.55f,bold,color));r.addView(cell(w,.55f,bold,color));r.addView(cell(d,.55f,bold,color));r.addView(cell(l,.55f,bold,color));r.addView(cell(gd,.7f,bold,color));r.addView(cell(pts,.8f,true,color));return r;}
    TextView cell(String s,float w,boolean bold,int color){TextView t=label(s,12,color,bold);t.setGravity(Gravity.CENTER_VERTICAL);t.setSingleLine(true);t.setTextSize(s.length()>14?10:12);t.setLayoutParams(new LinearLayout.LayoutParams(0,dp(34),w));return t;}

    
    String groupLineFor(String team) {
        if (team == null) return "";
        for (String g : data.groups) {
            ArrayList<String> names = new ArrayList<>();
            for (Match m : data.matches) {
                if (m.group.equals(g)) {
                    if (!names.contains(m.home)) names.add(m.home);
                    if (!names.contains(m.away)) names.add(m.away);
                }
            }
            if (names.contains(team)) {
                String line = "Group " + g + ": ";
                for (int i=0; i<names.size(); i++) {
                    if (i>0) line += ", ";
                    line += names.get(i);
                }
                return line;
            }
        }
        return "";
    }

    String groupOfTeam(String team) {
        if (team == null) return "";
        for (Match m : data.matches) {
            if (m.home.equals(team) || m.away.equals(team)) return m.group;
        }
        return "";
    }

void showPredictor(){
        clear(tr("Predict"),tr("Prediction Studio"),"Predict");
        LinearLayout c=card();c.addView(label("🔮 "+tr("Prediction Studio"),25,text,true));c.addView(label("Scores → tables → best thirds → bracket → champion → share poster.",15,subText,false));
        Button s=btn(tr("Enter Scores"));s.setOnClickListener(v->showMatches());c.addView(s);Button br=btn(tr("Pro Bracket"));br.setOnClickListener(v->showKnockout());c.addView(br);Button poster=btn(tr("Share Poster"));poster.setOnClickListener(v->showPoster());c.addView(poster);Button df=btn(tr("Dream Final"));df.setOnClickListener(v->showDreamFinal());c.addView(df);Button reset=outline(tr("Reset Scores"));reset.setOnClickListener(v->{data.reset();showPredictor();});c.addView(reset);
        LinearLayout q=card();q.addView(label("✅ Qualified teams preview",22,text,true));List<TeamRow> qual=data.qualified();for(int i=0;i<Math.min(qual.size(),16);i++){TeamRow r=qual.get(i);q.addView(label((i+1)+". "+flag(r.team)+" "+r.team+" • Group "+r.group+" • "+r.pts+" pts",14,subText,false));}
    }

    void showKnockout(){
        clear(tr("Pro Bracket"),"Round of 32 → Final","Predict");
        List<TeamRow> q=data.qualified();LinearLayout intro=card();intro.setBackground(gradient(RED_DARK,RED,dp(24)));intro.addView(label("🏆 Pro Bracket",26,Color.WHITE,true));intro.addView(label("Projected path from your group results.",15,Color.WHITE,false));
        String[] rounds={"Round of 32","Round of 16","Quarter-finals","Semi-finals","Final"};int games=16;
        for(String round:rounds){LinearLayout c=card();c.addView(label(round,23,RED,true));for(int i=0;i<games;i++){String a=q.size()>0?q.get((i*2)%q.size()).team:"TBD";String b=q.size()>1?q.get((i*2+1)%q.size()).team:"TBD";String w=winner(a,b);LinearLayout box=inner(c);box.addView(label(flag(a)+" "+a,15,text,true));box.addView(label("        │",12,subText,false));box.addView(label(flag(b)+" "+b,15,text,true));box.addView(label("Winner: "+flag(w)+" "+w,13,GREEN,true));}games=Math.max(1,games/2);}
        LinearLayout champ=card();champ.setBackground(gradient(RED_DARK,RED,dp(24)));String ch=q.size()>0?q.get(0).team:myTeam;champ.addView(label("🏆 Champion Pick",23,Color.WHITE,true));champ.addView(label(flag(ch)+" "+ch,36,GOLD,true));
    }
    LinearLayout inner(LinearLayout p){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(12),dp(10),dp(12),dp(10));l.setBackground(round(chipBg,dp(18),0));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.setMargins(0,dp(8),0,dp(8));p.addView(l,lp);return l;}
    String winner(String a,String b){if(a.equals(myTeam))return a;if(b.equals(myTeam))return b;return a.compareTo(b)<=0?a:b;}

    void showPoster(){
        clear(tr("Share Poster"),"Social prediction card","Predict");
        LinearLayout p=card();p.setBackground(gradient(RED_DARK,RED,dp(24)));p.addView(label("WORLD CUP FAN 2026",25,Color.WHITE,true));p.addView(label("MY TOURNAMENT PREDICTION",17,Color.WHITE,false));p.addView(label(flag(myTeam)+" "+tr("My Team")+": "+myTeam,22,Color.WHITE,true));List<TeamRow> q=data.qualified();String champ=q.size()>0?q.get(0).team:myTeam;p.addView(label("🏆 Champion: "+flag(champ)+" "+champ,25,GOLD,true));p.addView(label("Top 4\n"+topLines(4),16,Color.WHITE,false));p.addView(label("Made with World Cup Fan 2026",13,Color.WHITE,false));Button share=btn(tr("Share Poster"));share.setOnClickListener(v->sharePrediction());p.addView(share);
    }
    String topLines(int n){List<TeamRow> q=data.qualified();String s="";for(int i=0;i<Math.min(n,q.size());i++)s+=(i+1)+". "+flag(q.get(i).team)+" "+q.get(i).team+"\n";return s.length()==0?"TBD":s;}
    String topInline(int n){List<TeamRow> q=data.qualified();String s="";for(int i=0;i<Math.min(n,q.size());i++){if(i>0)s+=" • ";s+=flag(q.get(i).team)+" "+q.get(i).team;}return s.length()==0?"TBD":s;}

    void showDreamFinal(){clear(tr("Dream Final"),"Build a fan final","Predict");LinearLayout c=card();c.addView(label("🏆 "+tr("Dream Final"),25,text,true));Spinner left=spinner(data.teams,myTeam), right=spinner(data.teams,"Brazil");c.addView(label("Finalist 1",13,subText,true));c.addView(left);c.addView(label("Finalist 2",13,subText,true));c.addView(right);Button share=btn(tr("Share Poster"));share.setOnClickListener(v->{String a=left.getSelectedItem().toString(),b=right.getSelectedItem().toString();shareText("🏆 Dream Final 2026\n\n"+flag(a)+" "+a+" vs "+flag(b)+" "+b+"\n\nWorld Cup Fan 2026");});c.addView(share);}
    Spinner spinner(ArrayList<String> items,String sel){Spinner sp=new Spinner(this);ArrayAdapter<String> ad=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);sp.setAdapter(ad);int pos=items.indexOf(sel);if(pos>=0)sp.setSelection(pos);return sp;}

    void showMyTeam(){clear(tr("My Team"),"Team hub","More");LinearLayout c=card();c.addView(label(flag(myTeam)+" "+myTeam,28,RED,true));Spinner sp=spinner(data.teams,myTeam);c.addView(sp);Button save=btn("Save");save.setOnClickListener(v->{myTeam = sp.getSelectedItem().toString(); prefs.edit().putString("team", myTeam).apply(); toast("My Team saved"); showHome();});c.addView(save);LinearLayout path=card();path.addView(label("Road to Final",22,text,true));path.addView(pathPreview(myTeam));LinearLayout m=card();m.addView(label("⚽ "+tr("Matches"),22,text,true));for(Match ma:data.matches)if(ma.home.equals(myTeam)||ma.away.equals(myTeam))m.addView(label(ma.date+" • "+flag(ma.home)+" "+ma.home+" vs "+flag(ma.away)+" "+ma.away,14,subText,false));}
    void showCities(){clear(tr("Host Cities"),"USA • Mexico • Canada","More");String[][] cities={{"🇲🇽 Mexico City","Opening match energy and historic football culture."},{"🇨🇦 Toronto","Canada showcase city."},{"🇺🇸 Los Angeles","Entertainment capital and massive fan base."},{"🇺🇸 New York/New Jersey","Final atmosphere and huge global audience."},{"🇺🇸 Dallas","Huge stadium, huge matches."},{"🇺🇸 Miami","Latin football culture."},{"🇨🇦 Vancouver","West coast Canadian host."},{"🇺🇸 Atlanta","Modern stadium and fan festival city."},{"🇺🇸 Houston","International city."},{"🇺🇸 Kansas City","American soccer heartland."},{"🇺🇸 Boston","Historic sports city."},{"🇺🇸 Seattle","Strong supporter culture."}};for(String[] ci:cities){LinearLayout c=card();c.addView(label(ci[0],22,text,true));c.addView(label(ci[1],15,subText,false));}}

    
    void showVisualBracket() {
        clear("Visual Bracket", "Tournament tree preview", "Predict");
        ArrayList<String> q = new ArrayList<>();
        for (TeamRow r : data.qualified()) q.add(r.team);
        if (q.size() == 0) q.add(myTeam);

        String[] rounds = {"Round of 32", "Round of 16", "Quarter-finals", "Semi-finals", "Final"};
        int games = 16;
        for (String round : rounds) {
            LinearLayout c = card();
            c.addView(label("🏆 " + round, 23, RED, true));
            for (int i=0; i<games; i++) {
                String a = q.get((i*2) % q.size());
                String b = q.get((i*2+1) % q.size());
                String w = projectedWinner(a,b);
                TextView box = label("┌ " + flag(a) + " " + a + "\n├─ vs\n└ " + flag(b) + " " + b + "\n→ " + flag(w) + " " + w, 14, text, false);
                box.setPadding(dp(12), dp(10), dp(12), dp(10));
                box.setBackground(round(chipBg, dp(16), 1));
                c.addView(box);
            }
            games = Math.max(1, games/2);
        }
    }

    String projectedWinner(String a, String b) {
        if (a.equals(myTeam)) return a;
        if (b.equals(myTeam)) return b;
        int aa = teamPower(a), bb = teamPower(b);
        if (aa == bb) return a.compareTo(b) <= 0 ? a : b;
        return aa > bb ? a : b;
    }

    int teamPower(String t) {
        String elite = "Brazil Argentina France England Spain Germany Portugal Netherlands Belgium Croatia";
        String strong = "Uruguay Colombia Morocco Switzerland Denmark USA Mexico Japan Senegal";
        if (elite.contains(t)) return 90;
        if (strong.contains(t)) return 78;
        return 65;
    }

    void showScorers() {
        clear("Scorers", "Golden Boot simulation", "More");
        LinearLayout c = card();
        c.addView(label("🥇 Golden Boot Race", 24, text, true));
        c.addView(label("Simulated from team strength and your entered match scores.", 15, subText, false));
        String[] names = {"Kylian Mbappé","Lionel Messi","Harry Kane","Cristiano Ronaldo","Vinícius Jr","Jamal Musiala","Lamine Yamal","Luka Modrić","Erling Haaland","Mohamed Salah"};
        String[] teams = {"France","Argentina","England","Portugal","Brazil","Germany","Spain","Croatia","Norway","Egypt"};
        for (int i=0; i<names.length; i++) {
            int goals = Math.max(1, 8-i + (teamPower(teams[i])>80?1:0));
            c.addView(label((i+1)+". " + names[i] + " • " + flag(teams[i]) + " " + teams[i] + " • " + goals + " goals", 15, i<3?RED:subText, i<3));
        }
    }

    void showMvp() {
        clear("MVP", "Player of the tournament", "More");
        LinearLayout c = card();
        c.setBackground(gradient(RED_DARK, RED, dp(24)));
        String champ = data.qualified().size() > 0 ? data.qualified().get(0).team : myTeam;
        c.addView(label("⭐ Tournament MVP", 26, Color.WHITE, true));
        c.addView(label(flag(champ) + " " + champ + " leader", 25, GOLD, true));
        c.addView(label("MVP prediction is based on your current bracket and champion pick.", 15, Color.WHITE, false));
        LinearLayout s = card();
        s.addView(label("Shortlist", 22, text, true));
        s.addView(label("1. " + flag(champ) + " " + champ + " captain\n2. " + flag(myTeam) + " " + myTeam + " key player\n3. 🇫🇷 France star\n4. 🇧🇷 Brazil star\n5. 🇦🇷 Argentina star", 15, subText, false));
    }

    void showCompareTeams() {
        clear("Compare", "Team vs Team", "More");
        LinearLayout c = card();
        c.addView(label("⚔️ Team Comparison", 25, text, true));
        Spinner a = spinner(data.teams, myTeam);
        Spinner b = spinner(data.teams, "Brazil");
        c.addView(label("Team A", 13, subText, true)); c.addView(a);
        c.addView(label("Team B", 13, subText, true)); c.addView(b);
        Button compare = btn("Compare Teams");
        compare.setOnClickListener(v -> {
            String ta = a.getSelectedItem().toString();
            String tb = b.getSelectedItem().toString();
            showCompareResult(ta, tb);
        });
        c.addView(compare);
    }

    void showCompareResult(String a, String b) {
        clear("Compare", a + " vs " + b, "More");
        LinearLayout c = card();
        c.addView(label(flag(a) + " " + a + " vs " + flag(b) + " " + b, 22, text, true));
        int pa = teamPower(a), pb = teamPower(b);
        c.addView(label(a + " power: " + pa, 17, pa>=pb?GREEN:subText, true));
        c.addView(label(b + " power: " + pb, 17, pb>=pa?GREEN:subText, true));
        String pick = projectedWinner(a,b);
        c.addView(label("Projected winner: " + flag(pick) + " " + pick, 20, RED, true));
    }

    void showBackupExport() {
        clear("Backup", "Export / Import prediction", "More");
        LinearLayout c = card();
        c.addView(label("💾 Prediction Backup", 24, text, true));
        c.addView(label("Export code lets fans save or send their prediction. Import can be added later when we move the app from MVP to full account-free backup.", 15, subText, false));
        Button export = btn("Share Backup Code");
        export.setOnClickListener(v -> shareText(buildBackupCode()));
        c.addView(export);
    }

    String buildBackupCode() {
        String code = "WCF2026|" + myTeam + "|" + data.playedCount() + "|" + data.totalGoals() + "|" + data.progressPercent();
        return "My World Cup Fan 2026 backup code:\n\n" + code + "\n\nSave this text for later.";
    }

    void showPdfPosterInfo() {
        clear("PDF Poster", "Printable prediction poster", "More");
        LinearLayout c = card();
        c.addView(label("🖨️ PDF Prediction Poster", 24, text, true));
        c.addView(label("The app already has the poster layout. Next technical step is Android PDF export. For now, Share Poster creates a clean shareable prediction text/card.", 15, subText, false));
        Button open = btn("Open Share Poster");
        open.setOnClickListener(v -> showPoster());
        c.addView(open);
    }

    void showQuiz() {
        clear("World Cup Quiz", "Fan challenge", "More");
        LinearLayout c = card();
        c.addView(label("🧠 World Cup Quiz", 25, text, true));
        String[][] qa = {
            {"How many teams play in 2026?","48"},
            {"How many groups are there?","12"},
            {"How many teams are in each group?","4"},
            {"Which group is Croatia in?","L"},
            {"How many third-placed teams go through?","8"}
        };
        for (String[] q : qa) {
            TextView item = label("❓ " + q[0] + "\n✅ " + q[1], 15, subText, false);
            item.setPadding(dp(10), dp(8), dp(10), dp(8));
            item.setBackground(round(chipBg, dp(16), 1));
            c.addView(item);
        }
    }

    void showStadiumGuide() {
        clear("Stadium Guide", "Host stadium notes", "More");
        String[][] stadiums = {
            {"Azteca Stadium","Mexico City • iconic opening venue"},
            {"MetLife Stadium","New York/New Jersey • final-level stadium"},
            {"AT&T Stadium","Dallas • huge capacity"},
            {"SoFi Stadium","Los Angeles • modern showpiece"},
            {"Hard Rock Stadium","Miami • global fan city"},
            {"BC Place","Vancouver • Canadian west coast"}
        };
        for (String[] s : stadiums) {
            LinearLayout c = card();
            c.addView(label("🏟️ " + s[0], 22, text, true));
            c.addView(label(s[1], 15, subText, false));
        }
    }

    void showCityGuidePro() {
        clear("City Guide", "Fan travel notes", "More");
        String[][] cities = {
            {"Mexico City","Opening energy, football history, altitude factor."},
            {"Los Angeles","Entertainment, big stadium, global fan base."},
            {"New York/New Jersey","Final atmosphere and media center."},
            {"Dallas","Massive stadium and high-scoring indoor-style matches."},
            {"Miami","Latin football culture and beach city vibe."},
            {"Toronto","Canada showcase and multicultural fan base."}
        };
        for (String[] city : cities) {
            LinearLayout c = card();
            c.addView(label("🌎 " + city[0], 22, text, true));
            c.addView(label(city[1], 15, subText, false));
        }
    }

    void showAchievements() {
        clear("Achievements", "Badges and milestones", "More");
        LinearLayout c = card();
        c.addView(label("🏅 Fan Achievements", 25, text, true));
        int played = data.playedCount();
        c.addView(label((played>=1?"✅":"⬜") + " First Prediction", 17, played>=1?GREEN:subText, true));
        c.addView(label((played>=10?"✅":"⬜") + " Group Stage Analyst", 17, played>=10?GREEN:subText, true));
        c.addView(label((played>=24?"✅":"⬜") + " Tournament Expert", 17, played>=24?GREEN:subText, true));
        c.addView(label((data.progressPercent()>=100?"✅":"⬜") + " Prediction Master", 17, data.progressPercent()>=100?GOLD:subText, true));
        c.addView(label("My Team badge: " + flag(myTeam) + " " + myTeam + " Fan", 17, RED, true));
    }

void showMore(){
        clear(tr("More"),tr("Language")+" • Premium • Settings","More");
        LinearLayout langCard=card();langCard.addView(label("🌍 "+tr("Language"),22,text,true));LinearLayout langs=hrow();for(String l:new String[]{"EN","HR","DE","ES","FR"}){TextView cc=chip(l);if(l.equals(lang)){cc.setTextColor(Color.WHITE);cc.setBackground(gradient(RED_DARK,RED,dp(18)));}cc.setOnClickListener(v->{lang=((TextView)v).getText().toString();prefs.edit().putString("lang",lang).apply();redraw();});langs.addView(cc,new LinearLayout.LayoutParams(0,dp(44),1));}langCard.addView(langs);
        LinearLayout c=card();

        Button vb = btn("Visual Bracket"); vb.setOnClickListener(v -> showVisualBracket()); c.addView(vb);
        Button scorers = btn("Golden Boot"); scorers.setOnClickListener(v -> showScorers()); c.addView(scorers);
        Button mvp = btn("MVP Prediction"); mvp.setOnClickListener(v -> showMvp()); c.addView(mvp);
        Button compare = btn("Compare Teams"); compare.setOnClickListener(v -> showCompareTeams()); c.addView(compare);
        Button backup = btn("Export Backup"); backup.setOnClickListener(v -> showBackupExport()); c.addView(backup);
        Button pdf = btn("PDF Poster Info"); pdf.setOnClickListener(v -> showPdfPosterInfo()); c.addView(pdf);
        Button quiz = btn("World Cup Quiz"); quiz.setOnClickListener(v -> showQuiz()); c.addView(quiz);
        Button stadium = btn("Stadium Guide"); stadium.setOnClickListener(v -> showStadiumGuide()); c.addView(stadium);
        Button citypro = btn("City Guide Pro"); citypro.setOnClickListener(v -> showCityGuidePro()); c.addView(citypro);
        Button badges = btn("Achievements"); badges.setOnClickListener(v -> showAchievements()); c.addView(badges);
String[] names={tr("My Team"),tr("Statistics"),tr("Host Cities"),tr("Pro Bracket"),tr("Premium")};for(String n:names){Button b=btn(n);if(n.equals(tr("My Team")))b.setOnClickListener(v->showMyTeam());else if(n.equals(tr("Statistics")))b.setOnClickListener(v->showStats());else if(n.equals(tr("Host Cities")))b.setOnClickListener(v->showCities());else if(n.equals(tr("Pro Bracket")))b.setOnClickListener(v->showKnockout());else b.setOnClickListener(v->showPremium());c.addView(b);}
        Button cro = btn(myTeam + " Road to Final"); cro.setOnClickListener(v -> showCroatiaRoad()); c.addView(cro);
        Button facts = btn("World Cup Facts"); facts.setOnClickListener(v -> showWorldCupFacts()); c.addView(facts);
        Button launch = btn("Play Store Checklist"); launch.setOnClickListener(v -> showPlayStoreChecklist()); c.addView(launch);
        Button privacy = btn("Privacy / Legal"); privacy.setOnClickListener(v -> showPrivacyInfo()); c.addView(privacy);
        Button mode=btn(darkMode?tr("Switch to Light Mode"):tr("Switch to Dark Mode"));mode.setOnClickListener(v->{darkMode=!darkMode;prefs.edit().putBoolean("darkMode",darkMode).apply();redraw();});c.addView(mode);
        LinearLayout l=card();l.addView(label("Version 9.0 Play Store Polish Edition",22,RED,false));l.addView(label("Independent fan-made app. No official FIFA logo, no official crests, no player photos, no live streaming.",15,subText,false));
    }

    void showPremium(){clear(tr("Premium"),tr("Free vs Pro"),"More");LinearLayout c=card();c.setBackground(gradient(RED_DARK,RED,dp(24)));c.addView(label("💎 World Cup Fan Pro",27,Color.WHITE,true));c.addView(label("Suggested price: €1.99",20,GOLD,true));c.addView(label("FREE\n• Basic scores\n• Group tables\n• One prediction\n\nPRO\n• Multiple saved predictions\n• Share poster\n• Advanced statistics\n• Dream finals\n• Premium themes\n• Export PDF/PNG\n• Match reminders\n• Custom tournaments",16,Color.WHITE,false));}
    void showStats(){clear(tr("Statistics"),"Advanced insights","More");LinearLayout c=card();c.addView(label("📊 "+tr("Statistics"),25,text,true));c.addView(kpiRow("Played",""+data.playedCount(),"Goals",""+data.totalGoals(),"Progress",data.progressPercent()+"%",false));c.addView(label("Average goals: "+data.avgGoals(),18,subText,false));c.addView(label("Best attack: "+data.bestAttack(),18,RED,true));c.addView(label("Highest scoring group: "+data.bestGroup(),18,GREEN,true));}
    void sharePrediction(){shareText("⚽ World Cup Fan 2026\n\n"+tr("My Team")+": "+flag(myTeam)+" "+myTeam+"\nChampion pick: "+topInline(1)+"\nTop 4: "+topInline(4)+"\nProgress: "+data.progressPercent()+"%\n\nWorld Cup Fan 2026");}
    void shareText(String msg){Intent send=new Intent(Intent.ACTION_SEND);send.setType("text/plain");send.putExtra(Intent.EXTRA_TEXT,msg);startActivity(Intent.createChooser(send,"Share"));hideSystemBars();}
    
    void showPlayStoreChecklist() {
        clear("Play Store", "Launch readiness checklist", "More");
        LinearLayout c = card();
        c.addView(label("🚀 Play Store Launch Checklist", 24, text, true));
        c.addView(label("Status: almost ready for private/internal testing.", 15, subText, false));
        c.addView(label("✅ Offline tournament simulator\n✅ All 48 teams loaded\n✅ Group tables\n✅ My Team hub\n✅ Croatia Road to Final\n✅ Multi-language menu\n✅ Dark/Light mode\n✅ Share Prediction\n✅ Premium positioning", 15, subText, false));
        LinearLayout todo = card();
        todo.addView(label("Before public release", 22, RED, true));
        todo.addView(label("1. Verify every official fixture date and venue.\n2. Do not use FIFA logo or official crests.\n3. Add real app icon and screenshots.\n4. Prepare privacy policy.\n5. Test on at least 3 phones.\n6. Build release AAB for Play Console.", 15, subText, false));
    }

    void showCroatiaRoad() {
        clear(myTeam + " Road", "Group "+groupOfTeam(myTeam)+" and path to final", "More");
        LinearLayout hero = card();
        hero.setBackground(gradient(RED_DARK, RED, dp(24)));
        hero.addView(label(flag(myTeam) + " " + myTeam + " Road to Final", 25, Color.WHITE, true));
        hero.addView(label(groupLineFor(myTeam), 16, Color.WHITE, false));
        LinearLayout g = card();
        g.addView(label("Group "+groupOfTeam(myTeam)+" matches", 22, text, true));
        for (Match m : data.matches) {
            if (m.group.equals(groupOfTeam(myTeam)) || m.home.equals(myTeam) || m.away.equals(myTeam)) {
                g.addView(label(m.date + " • " + flag(m.home) + " " + m.home + " vs " + flag(m.away) + " " + m.away, 14, subText, false));
            }
        }
        LinearLayout road = card();
        road.addView(label("Projected path", 22, text, true));
        road.addView(label("Group "+groupOfTeam(myTeam)+" → Round of 32 → Round of 16 → Quarter-final → Semi-final → Final", 16, GREEN, true));
        road.addView(label("This is the emotional hook for Croatian users, while the app stays global for every fan.", 14, subText, false));
    }

    void showWorldCupFacts() {
        clear("Facts", "Tournament quick facts", "More");
        String[] facts = {
            "48 teams • 12 groups • 104 matches",
            "Top 2 from each group qualify",
            "Best 8 third-placed teams qualify",
            "Knockout starts with Round of 32",
            "Host countries: USA, Mexico and Canada",
            "App works offline after installation"
        };
        for (String f : facts) {
            LinearLayout c = card();
            c.addView(label("⚽ " + f, 19, text, true));
        }
    }

    void showPrivacyInfo() {
        clear("Privacy", "Simple privacy statement", "More");
        LinearLayout c = card();
        c.addView(label("🔒 Privacy", 24, text, true));
        c.addView(label("World Cup Fan 2026 works offline. Scores, selected team, language and theme are stored only on this device. No login, no personal profile and no official football data ownership is claimed.", 15, subText, false));
        LinearLayout l = card();
        l.addView(label("Legal safety", 22, RED, true));
        l.addView(label("Independent fan-made app. No official FIFA logo, no official crests, no player photos, no streaming and no betting services.", 15, subText, false));
    }

void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}

    String flag(String t){if(t==null)return"🏳";String[][] f={{"Croatia","🇭🇷"},{"Brazil","🇧🇷"},{"Germany","🇩🇪"},{"Argentina","🇦🇷"},{"France","🇫🇷"},{"Spain","🇪🇸"},{"Portugal","🇵🇹"},{"England","🏴"},{"Netherlands","🇳🇱"},{"Belgium","🇧🇪"},{"Mexico","🇲🇽"},{"USA","🇺🇸"},{"Canada","🇨🇦"},{"Japan","🇯🇵"},{"Korea Republic","🇰🇷"},{"South Africa","🇿🇦"},{"Czechia","🇨🇿"},{"Bosnia and Herzegovina","🇧🇦"},{"Qatar","🇶🇦"},{"Switzerland","🇨🇭"},{"Morocco","🇲🇦"},{"Haiti","🇭🇹"},{"Scotland","🏴"},{"Paraguay","🇵🇾"},{"Australia","🇦🇺"},{"Turkey","🇹🇷"},{"Curacao","🇨🇼"},{"Ivory Coast","🇨🇮"},{"Ecuador","🇪🇨"},{"Sweden","🇸🇪"},{"Tunisia","🇹🇳"},{"Egypt","🇪🇬"},{"Iran","🇮🇷"},{"New Zealand","🇳🇿"},{"Cape Verde","🇨🇻"},{"Saudi Arabia","🇸🇦"},{"Uruguay","🇺🇾"},{"Senegal","🇸🇳"},{"Iraq","🇮🇶"},{"Norway","🇳🇴"},{"Algeria","🇩🇿"},{"Austria","🇦🇹"},{"Jordan","🇯🇴"},{"DR Congo","🇨🇩"},{"Uzbekistan","🇺🇿"},{"Colombia","🇨🇴"},{"Ghana","🇬🇭"},{"Panama","🇵🇦"}};for(String[] x:f)if(t.equals(x[0]))return x[1];return"🏳";}
    Drawable gradient(int a,int b,int radius){GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{a,b});g.setCornerRadius(radius);return g;}
    Drawable round(int color,int radius,int sw){GradientDrawable g=new GradientDrawable();g.setColor(color);g.setCornerRadius(radius);if(sw>0)g.setStroke(sw,stroke);return g;}
    int dp(int v){return(int)(v*getResources().getDisplayMetrics().density+0.5f);}

    static class Match{String group,home,away,date,venue;int homeGoals=-1,awayGoals=-1;Match(String g,String h,String a,String d,String v){group=g;home=h;away=a;date=d;venue=v;}}
    static class TeamRow{String group,team;int pts,gf,ga,w,d,l,p;TeamRow(String g,String t){group=g;team=t;}}
    class Data{
        String[] groups={"A","B","C","D","E","F","G","H","I","J","K","L"};ArrayList<String> teams=new ArrayList<>();ArrayList<Match> matches=new ArrayList<>();SharedPreferences sp;
        Data(Context c){sp=c.getSharedPreferences("scores",0);seed();load();}
        void seed(){String[][] g={{"Mexico","South Africa","Korea Republic","Czechia"},{"Canada","Bosnia and Herzegovina","Qatar","Switzerland"},{"Brazil","Morocco","Haiti","Scotland"},{"USA","Paraguay","Australia","Turkey"},{"Germany","Curacao","Ivory Coast","Ecuador"},{"Netherlands","Japan","Sweden","Tunisia"},{"Belgium","Egypt","Iran","New Zealand"},{"Spain","Cape Verde","Saudi Arabia","Uruguay"},{"France","Senegal","Iraq","Norway"},{"Argentina","Algeria","Austria","Jordan"},{"Portugal","DR Congo","Uzbekistan","Colombia"},{"England","Croatia","Ghana","Panama"}};String[] venues={"Mexico City","Toronto","Los Angeles","New York/New Jersey","Dallas","Miami","Vancouver","Atlanta","Houston","Kansas City","Boston","Seattle"};int day=11;for(int x=0;x<g.length;x++){String gr=groups[x];for(String t:g[x])teams.add(t);String[] t=g[x];matches.add(new Match(gr,t[0],t[1],"2026-06-"+(day+x%15),venues[x]));matches.add(new Match(gr,t[2],t[3],"2026-06-"+(day+1+x%15),venues[(x+3)%venues.length]));matches.add(new Match(gr,t[0],t[2],"2026-06-"+(day+6+x%12),venues[(x+6)%venues.length]));matches.add(new Match(gr,t[1],t[3],"2026-06-"+(day+7+x%12),venues[(x+8)%venues.length]));matches.add(new Match(gr,t[0],t[3],"2026-06-"+(day+12+x%7),venues[(x+9)%venues.length]));matches.add(new Match(gr,t[1],t[2],"2026-06-"+(day+12+x%7),venues[(x+10)%venues.length]));}Collections.sort(teams);}
        void load(){for(int i=0;i<matches.size();i++){matches.get(i).homeGoals=sp.getInt("h"+i,-1);matches.get(i).awayGoals=sp.getInt("a"+i,-1);}}
        void save(){SharedPreferences.Editor e=sp.edit();for(int i=0;i<matches.size();i++){e.putInt("h"+i,matches.get(i).homeGoals);e.putInt("a"+i,matches.get(i).awayGoals);}e.apply();}
        void reset(){sp.edit().clear().apply();for(Match m:matches){m.homeGoals=-1;m.awayGoals=-1;}}
        Map<String,List<TeamRow>> standings(){Map<String,List<TeamRow>> out=new LinkedHashMap<>();for(String gr:groups){Map<String,TeamRow> m=new LinkedHashMap<>();for(Match ma:matches)if(ma.group.equals(gr)){m.putIfAbsent(ma.home,new TeamRow(gr,ma.home));m.putIfAbsent(ma.away,new TeamRow(gr,ma.away));if(ma.homeGoals>=0&&ma.awayGoals>=0){TeamRow h=m.get(ma.home),a=m.get(ma.away);h.p++;a.p++;h.gf+=ma.homeGoals;h.ga+=ma.awayGoals;a.gf+=ma.awayGoals;a.ga+=ma.homeGoals;if(ma.homeGoals>ma.awayGoals){h.w++;h.pts+=3;a.l++;}else if(ma.homeGoals<ma.awayGoals){a.w++;a.pts+=3;h.l++;}else{h.d++;a.d++;h.pts++;a.pts++;}}}ArrayList<TeamRow> list=new ArrayList<>(m.values());Collections.sort(list,(x,y)->y.pts!=x.pts?y.pts-x.pts:((y.gf-y.ga)!=(x.gf-x.ga)?(y.gf-y.ga)-(x.gf-x.ga):y.gf-x.gf));out.put(gr,list);}return out;}
        List<TeamRow> bestThirds(){ArrayList<TeamRow> third=new ArrayList<>();for(List<TeamRow> l:standings().values())if(l.size()>2)third.add(l.get(2));Collections.sort(third,(x,y)->y.pts!=x.pts?y.pts-x.pts:((y.gf-y.ga)!=(x.gf-x.ga)?(y.gf-y.ga)-(x.gf-x.ga):y.gf-x.gf));return third;}
        List<TeamRow> qualified(){ArrayList<TeamRow> q=new ArrayList<>();for(List<TeamRow> l:standings().values()){if(l.size()>0)q.add(l.get(0));if(l.size()>1)q.add(l.get(1));}List<TeamRow> th=bestThirds();for(int i=0;i<Math.min(8,th.size());i++)q.add(th.get(i));return q;}
        int playedCount(){int c=0;for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0)c++;return c;}int totalGoals(){int g=0;for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0)g+=m.homeGoals+m.awayGoals;return g;}String avgGoals(){return playedCount()==0?"0.00":String.format(Locale.US,"%.2f",totalGoals()*1.0/playedCount());}int progressPercent(){return(int)Math.round(playedCount()*100.0/matches.size());}String bestAttack(){Map<String,Integer> gf=new HashMap<>();for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0){gf.put(m.home,gf.getOrDefault(m.home,0)+m.homeGoals);gf.put(m.away,gf.getOrDefault(m.away,0)+m.awayGoals);}String best="TBD";int max=-1;for(String t:gf.keySet())if(gf.get(t)>max){max=gf.get(t);best=t;}return best+" ("+Math.max(0,max)+")";}String bestGroup(){Map<String,Integer> goals=new HashMap<>();for(Match m:matches)if(m.homeGoals>=0&&m.awayGoals>=0)goals.put(m.group,goals.getOrDefault(m.group,0)+m.homeGoals+m.awayGoals);String best="TBD";int max=-1;for(String g:goals.keySet())if(goals.get(g)>max){max=goals.get(g);best=g;}return best+" ("+Math.max(0,max)+" goals)";}Match nextFor(String team){for(Match m:matches)if((m.home.equals(team)||m.away.equals(team))&&m.homeGoals<0)return m;return null;}
    }
}
