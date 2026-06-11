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
        
        else if(key.equals("Visual Bracket")){hr="Vizualni kostur";de="Turnierbaum";es="Cuadro visual";fr="Tableau visuel";}
        else if(key.equals("Golden Boot")){hr="Zlatna kopačka";de="Goldener Schuh";es="Bota de Oro";fr="Soulier d'Or";}
        else if(key.equals("MVP Prediction")){hr="MVP prognoza";de="MVP Prognose";es="Predicción MVP";fr="Prédiction MVP";}
        else if(key.equals("Compare Teams")){hr="Usporedi timove";de="Teams vergleichen";es="Comparar equipos";fr="Comparer équipes";}
        else if(key.equals("Export Backup")){hr="Izvoz kopije";de="Backup exportieren";es="Exportar copia";fr="Exporter sauvegarde";}
        else if(key.equals("PDF Poster Info")){hr="PDF poster info";de="PDF Poster Info";es="Info póster PDF";fr="Info poster PDF";}
        else if(key.equals("World Cup Quiz")){hr="SP kviz";de="WM Quiz";es="Quiz Mundial";fr="Quiz Coupe du Monde";}
        else if(key.equals("Stadium Guide")){hr="Vodič stadiona";de="Stadionführer";es="Guía de estadios";fr="Guide des stades";}
        else if(key.equals("City Guide Pro")){hr="Vodič gradova Pro";de="Städteführer Pro";es="Guía de ciudades Pro";fr="Guide des villes Pro";}
        else if(key.equals("Achievements")){hr="Postignuća";de="Erfolge";es="Logros";fr="Succès";}
        else if(key.equals("Tournament tree preview")){hr="Pregled turnirskog stabla";de="Vorschau Turnierbaum";es="Vista del cuadro";fr="Aperçu du tableau";}
        else if(key.equals("Golden Boot simulation")){hr="Simulacija zlatne kopačke";de="Goldener Schuh Simulation";es="Simulación Bota de Oro";fr="Simulation Soulier d'Or";}
        else if(key.equals("Player of the tournament")){hr="Igrač turnira";de="Spieler des Turniers";es="Jugador del torneo";fr="Joueur du tournoi";}
        else if(key.equals("Team vs Team")){hr="Tim protiv tima";de="Team gegen Team";es="Equipo contra equipo";fr="Équipe contre équipe";}
        else if(key.equals("Export / Import prediction")){hr="Izvoz / uvoz prognoze";de="Prognose export/import";es="Exportar / importar predicción";fr="Exporter / importer pronostic";}
        else if(key.equals("Printable prediction poster")){hr="Poster prognoze za print";de="Druckbares Prognoseposter";es="Póster imprimible";fr="Poster imprimable";}
        else if(key.equals("Fan challenge")){hr="Navijački izazov";de="Fan Herausforderung";es="Reto de fans";fr="Défi des fans";}
        else if(key.equals("Host stadium notes")){hr="Bilješke o stadionima";de="Stadion Notizen";es="Notas de estadios";fr="Notes stades";}
        else if(key.equals("Fan travel notes")){hr="Navijačke putne bilješke";de="Fan Reisehinweise";es="Notas de viaje";fr="Notes voyage fans";}
        else if(key.equals("Badges and milestones")){hr="Značke i postignuća";de="Abzeichen und Meilensteine";es="Insignias e hitos";fr="Badges et étapes";}
        else if(key.equals("Launch readiness checklist")){hr="Popis spremnosti za objavu";de="Checkliste Veröffentlichung";es="Lista de lanzamiento";fr="Liste de lancement";}
        else if(key.equals("Privacy")){hr="Privatnost";de="Datenschutz";es="Privacidad";fr="Confidentialité";}
        else if(key.equals("Legal safety")){hr="Pravna sigurnost";de="Rechtliche Sicherheit";es="Seguridad legal";fr="Sécurité juridique";}

        
        else if(key.equals("Host Cities Pro")){hr="Gradovi domaćini Pro";de="Gastgeberstädte Pro";es="Ciudades sede Pro";fr="Villes hôtes Pro";}
        else if(key.equals("Team Hub Pro")){hr="Centar reprezentacije Pro";de="Team-Zentrale Pro";es="Centro equipo Pro";fr="Hub équipe Pro";}
        else if(key.equals("Onboarding Preview")){hr="Uvodni ekran";de="Einführung";es="Introducción";fr="Introduction";}
        else if(key.equals("Poster Export Plan")){hr="Plan izvoza postera";de="Poster-Export Plan";es="Plan exportar póster";fr="Plan export poster";}
        else if(key.equals("Tournament Rules")){hr="Pravila turnira";de="Turnierregeln";es="Reglas torneo";fr="Règles tournoi";}
        else if(key.equals("Monetization Plan")){hr="Plan zarade";de="Monetarisierung";es="Monetización";fr="Monétisation";}

        
        else if(key.equals("Host Cities")){hr="Gradovi domaćini";de="Gastgeberstädte";es="Ciudades sede";fr="Villes hôtes";}
        else if(key.equals("Players")){hr="Igrači";de="Spieler";es="Jugadores";fr="Joueurs";}
        else if(key.equals("Top scorers prediction")){hr="Prognoza najboljih strijelaca";de="Torschützen-Prognose";es="Predicción de goleadores";fr="Pronostic buteurs";}
        else if(key.equals("Player of the tournament")){hr="Igrač turnira";de="Spieler des Turniers";es="Jugador del torneo";fr="Joueur du tournoi";}
        else if(key.equals("Star watch")){hr="Zvijezde turnira";de="Stars im Blick";es="Estrellas a seguir";fr="Stars à suivre";}
        else if(key.equals("Shortlist")){hr="Uži izbor";de="Auswahlliste";es="Lista corta";fr="Liste courte";}
        else if(key.equals("goals")){hr="golova";de="Tore";es="goles";fr="buts";}
        else if(key.equals("Privacy / Legal")){hr="Privatnost / Pravno";de="Datenschutz / Rechtliches";es="Privacidad / Legal";fr="Confidentialité / Légal";}

        
        else if(key.equals("About App")){hr="O aplikaciji";de="Über die App";es="Acerca de la app";fr="À propos";}
        else if(key.equals("Privacy Policy")){hr="Pravila privatnosti";de="Datenschutzerklärung";es="Política de privacidad";fr="Politique de confidentialité";}
        else if(key.equals("App version")){hr="Verzija aplikacije";de="App-Version";es="Versión de la app";fr="Version de l'app";}
        else if(key.equals("Independent fan-made app")){hr="Nezavisna navijačka aplikacija";de="Unabhängige Fan-App";es="Aplicación independiente de fans";fr="Application indépendante de fans";}
        else if(key.equals("No login. No account. No personal profile.")){hr="Bez prijave. Bez računa. Bez osobnog profila.";de="Kein Login. Kein Konto. Kein persönliches Profil.";es="Sin inicio de sesión. Sin cuenta. Sin perfil personal.";fr="Pas de connexion. Pas de compte. Pas de profil personnel.";}
        else if(key.equals("Data is stored only on this device.")){hr="Podaci se spremaju samo na ovom uređaju.";de="Daten werden nur auf diesem Gerät gespeichert.";es="Los datos se guardan solo en este dispositivo.";fr="Les données sont stockées uniquement sur cet appareil.";}
        else if(key.equals("Scores, language, theme and selected team are saved locally.")){hr="Rezultati, jezik, tema i odabrana reprezentacija spremaju se lokalno.";de="Ergebnisse, Sprache, Design und ausgewähltes Team werden lokal gespeichert.";es="Resultados, idioma, tema y equipo elegido se guardan localmente.";fr="Scores, langue, thème et équipe choisie sont enregistrés localement.";}
        else if(key.equals("This app is not affiliated with FIFA.")){hr="Aplikacija nije povezana s FIFA-om.";de="Diese App ist nicht mit FIFA verbunden.";es="Esta app no está afiliada a FIFA.";fr="Cette application n'est pas affiliée à la FIFA.";}

        
        else if(key.equals("Tournament is live")){hr="Prvenstvo je u tijeku";de="Das Turnier läuft";es="El torneo está en marcha";fr="Le tournoi est en cours";}
        else if(key.equals("Day 1 of the World Cup")){hr="Dan 1 Svjetskog prvenstva";de="Tag 1 der Weltmeisterschaft";es="Día 1 del Mundial";fr="Jour 1 de la Coupe du Monde";}
        else if(key.equals("Take quiz")){hr="Riješi kviz";de="Quiz starten";es="Hacer quiz";fr="Faire le quiz";}
        else if(key.equals("Score")){hr="Bodovi";de="Punkte";es="Puntuación";fr="Score";}
        else if(key.equals("Try again")){hr="Pokušaj ponovno";de="Erneut versuchen";es="Intentar de nuevo";fr="Réessayer";}
        else if(key.equals("Correct")){hr="Točno";de="Richtig";es="Correcto";fr="Correct";}
        else if(key.equals("Wrong")){hr="Netočno";de="Falsch";es="Incorrecto";fr="Incorrect";}
        else if(key.equals("Choose answer")){hr="Odaberi odgovor";de="Antwort wählen";es="Elige respuesta";fr="Choisis une réponse";}
        else if(key.equals("Global Edition")){hr="Globalno izdanje";de="Globale Ausgabe";es="Edición global";fr="Édition mondiale";}
        else if(key.equals("Progress")){hr="Napredak";de="Fortschritt";es="Progreso";fr="Progression";}
        else if(key.equals("Played")){hr="Odigrano";de="Gespielt";es="Jugados";fr="Joués";}
        else if(key.equals("Goals")){hr="Golovi";de="Tore";es="Goles";fr="Buts";}

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
        subtitle=label("v13.10 Play Store Polish Edition • 5 languages • Immersive fullscreen",14,Color.WHITE,false);
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

    
    
    
    String groupLine(String team){
        if(team.equals("Mexico")||team.equals("South Africa")||team.equals("Korea Republic")||team.equals("Czechia"))return "Group A: Mexico, South Africa, Korea Republic, Czechia";
        if(team.equals("Canada")||team.equals("Bosnia and Herzegovina")||team.equals("Qatar")||team.equals("Switzerland"))return "Group B: Canada, Bosnia and Herzegovina, Qatar, Switzerland";
        if(team.equals("Brazil")||team.equals("Morocco")||team.equals("Haiti")||team.equals("Scotland"))return "Group C: Brazil, Morocco, Haiti, Scotland";
        if(team.equals("USA")||team.equals("Paraguay")||team.equals("Australia")||team.equals("Turkey"))return "Group D: USA, Paraguay, Australia, Turkey";
        if(team.equals("Germany")||team.equals("Curaçao")||team.equals("Cote d'Ivoire")||team.equals("Ecuador"))return "Group E: Germany, Curaçao, Cote d'Ivoire, Ecuador";
        if(team.equals("Netherlands")||team.equals("Japan")||team.equals("Sweden")||team.equals("Tunisia"))return "Group F: Netherlands, Japan, Sweden, Tunisia";
        if(team.equals("Belgium")||team.equals("Egypt")||team.equals("Iran")||team.equals("New Zealand"))return "Group G: Belgium, Egypt, Iran, New Zealand";
        if(team.equals("Spain")||team.equals("Cape Verde")||team.equals("Saudi Arabia")||team.equals("Uruguay"))return "Group H: Spain, Cape Verde, Saudi Arabia, Uruguay";
        if(team.equals("France")||team.equals("Senegal")||team.equals("Iraq")||team.equals("Norway"))return "Group I: France, Senegal, Iraq, Norway";
        if(team.equals("Argentina")||team.equals("Algeria")||team.equals("Austria")||team.equals("Jordan"))return "Group J: Argentina, Algeria, Austria, Jordan";
        if(team.equals("Portugal")||team.equals("DR Congo")||team.equals("Uzbekistan")||team.equals("Colombia"))return "Group K: Portugal, DR Congo, Uzbekistan, Colombia";
        if(team.equals("England")||team.equals("Croatia")||team.equals("Ghana")||team.equals("Panama"))return "Group L: England, Croatia, Ghana, Panama";
        return "Group: "+team;
    }




    String t2(String en,String hr,String de,String es,String fr){
        if(lang.equals("HR"))return hr;
        if(lang.equals("DE"))return de;
        if(lang.equals("ES"))return es;
        if(lang.equals("FR"))return fr;
        return en;
    }

void showHome(){
        clear("World Cup Fan 2026","v13.10 • "+lang+" • Global fan app","Home");

        LinearLayout launch=card();
        launch.setBackground(gradient(RED_DARK,RED,dp(24)));
        launch.addView(label("🏁 "+tr("Tournament is live"),25,Color.WHITE,true));
        launch.addView(label(tr("Day 1 of the World Cup"),16,Color.WHITE,false));

        LinearLayout hero=card();
        hero.setBackground(gradient(RED_DARK,RED,dp(24)));
        hero.addView(label("🌍 Globalno izdanje v13.10",24,Color.WHITE,true));
        hero.addView(label(t2("Fullscreen, 5 languages, team hub, clean predictor, group tables and share-ready poster.","Prikaz preko cijelog zaslona, 5 jezika, centar reprezentacije, čista prognoza, tablice skupina i poster za dijeljenje.","Vollbild, 5 Sprachen, Team-Hub, Gruppentabellen und teilbarer Poster.","Pantalla completa, 5 idiomas, centro de equipo, tablas y póster para compartir.","Plein écran, 5 langues, hub équipe, tableaux et poster partageable."),15,Color.WHITE,false));
        hero.addView(kpiRow(tr("Progress"),data.progressPercent()+"%",tr("Played"),""+data.playedCount(),tr("Goals"),""+data.totalGoals(),true));

        LinearLayout countCard=card();
        countCard.addView(label("⚽ "+tr("Tournament is live"),22,text,true));
        countCard.addView(label(tr("Day 1 of the World Cup"),30,RED,true));

        LinearLayout my=card();
        my.addView(label(flag(myTeam)+" "+myTeam+" "+t2("Road to Final","Put do finala","Weg ins Finale","Camino a la final","Route vers la finale"),24,text,true));
        my.addView(label(groupLine(myTeam),15,subText,false));
        String[] stages={tr("Group"),tr("Round of 32"),tr("Round of 16"),tr("Quarter-final"),tr("Semi-final"),tr("Final")};
        for(String st:stages)my.addView(label(flag(myTeam)+" "+myTeam+" → "+st,15,subText,false));
        Button hub=btn(tr("Team Hub"));
        hub.setOnClickListener(v->showMyTeam());
        my.addView(hub);

        LinearLayout pred=card();
        pred.addView(label("🔮 "+tr("Prediction Studio"),24,text,true));
        pred.addView(label(t2("Scores → tables → best thirds → bracket → champion → share poster.","Rezultati → tablice → najbolje trećeplasirane → kostur → prvak → poster.","Ergebnisse → Tabellen → beste Dritte → Baum → Sieger → Poster.","Resultados → tablas → mejores terceros → cuadro → campeón → póster.","Scores → tableaux → meilleurs troisièmes → tableau → champion → poster."),15,subText,false));
        Button start=btn(tr("Start Simulator"));
        start.setOnClickListener(v->showPredictor());
        pred.addView(start);
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
        LinearLayout top=card(); top.addView(label("🔎 Smart Match Center",21,text,true));
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
        LinearLayout c=card();c.addView(label("🔮 Prediction Studio",25,text,true));c.addView(label("Scores → tables → best thirds → bracket → champion → share poster.",15,subText,false));
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
        LinearLayout p=card();p.setBackground(gradient(RED_DARK,RED,dp(24)));p.addView(label("WORLD CUP FAN 2026",25,Color.WHITE,true));p.addView(label("MY TOURNAMENT PREDICTION",17,Color.WHITE,false));p.addView(label(flag(myTeam)+" My Team: "+myTeam,22,Color.WHITE,true));List<TeamRow> q=data.qualified();String champ=q.size()>0?q.get(0).team:myTeam;p.addView(label("🏆 Champion: "+flag(champ)+" "+champ,25,GOLD,true));p.addView(label("Top 4\n"+topLines(4),16,Color.WHITE,false));p.addView(label("Made with World Cup Fan 2026",13,Color.WHITE,false));Button share=btn(tr("Share Poster"));share.setOnClickListener(v->sharePrediction());p.addView(share);
    }
    String topLines(int n){List<TeamRow> q=data.qualified();String s="";for(int i=0;i<Math.min(n,q.size());i++)s+=(i+1)+". "+flag(q.get(i).team)+" "+q.get(i).team+"\n";return s.length()==0?"TBD":s;}
    String topInline(int n){List<TeamRow> q=data.qualified();String s="";for(int i=0;i<Math.min(n,q.size());i++){if(i>0)s+=" • ";s+=flag(q.get(i).team)+" "+q.get(i).team;}return s.length()==0?"TBD":s;}

    void showDreamFinal(){clear(tr("Dream Final"),"Build a fan final","Predict");LinearLayout c=card();c.addView(label("🏆 Dream Final",25,text,true));Spinner left=spinner(data.teams,myTeam), right=spinner(data.teams,"Brazil");c.addView(label("Finalist 1",13,subText,true));c.addView(left);c.addView(label("Finalist 2",13,subText,true));c.addView(right);Button share=btn(tr("Share Poster"));share.setOnClickListener(v->{String a=left.getSelectedItem().toString(),b=right.getSelectedItem().toString();shareText("🏆 Dream Final 2026\n\n"+flag(a)+" "+a+" vs "+flag(b)+" "+b+"\n\nWorld Cup Fan 2026");});c.addView(share);}
    Spinner spinner(ArrayList<String> items,String sel){Spinner sp=new Spinner(this);ArrayAdapter<String> ad=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);sp.setAdapter(ad);int pos=items.indexOf(sel);if(pos>=0)sp.setSelection(pos);return sp;}

    void showMyTeam(){clear(tr("My Team"),"Team hub","More");LinearLayout c=card();c.addView(label(flag(myTeam)+" "+myTeam,28,RED,true));Spinner sp=spinner(data.teams,myTeam);c.addView(sp);Button save=btn("Save");save.setOnClickListener(v->{myTeam = sp.getSelectedItem().toString(); prefs.edit().putString("team", myTeam).apply(); toast("My Team saved"); showHome();});c.addView(save);LinearLayout path=card();path.addView(label(tr("Road to Final"),22,text,true));path.addView(pathPreview(myTeam));LinearLayout m=card();m.addView(label("⚽ Matches",22,text,true));for(Match ma:data.matches)if(ma.home.equals(myTeam)||ma.away.equals(myTeam))m.addView(label(ma.date+" • "+flag(ma.home)+" "+ma.home+" vs "+flag(ma.away)+" "+ma.away,14,subText,false));}
    void showCities(){clear(tr("Host Cities"),"USA • Mexico • Canada","More");String[][] cities={{"🇲🇽 Mexico City","Opening match energy and historic football culture."},{"🇨🇦 Toronto","Canada showcase city."},{"🇺🇸 Los Angeles","Entertainment capital and massive fan base."},{"🇺🇸 New York/New Jersey","Final atmosphere and huge global audience."},{"🇺🇸 Dallas","Huge stadium, huge matches."},{"🇺🇸 Miami","Latin football culture."},{"🇨🇦 Vancouver","West coast Canadian host."},{"🇺🇸 Atlanta","Modern stadium and fan festival city."},{"🇺🇸 Houston","International city."},{"🇺🇸 Kansas City","American soccer heartland."},{"🇺🇸 Boston","Historic sports city."},{"🇺🇸 Seattle","Strong supporter culture."}};for(String[] ci:cities){LinearLayout c=card();c.addView(label(ci[0],22,text,true));c.addView(label(ci[1],15,subText,false));}}

    
    void showVisualBracket() {
        clear(tr("Visual Bracket"), tr("Tournament tree preview"), "Predict");
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

    void showScorers(){
        clear(tr("Golden Boot"),tr("Top scorers prediction"),"More");
        LinearLayout c=card();
        c.addView(label("🥇 Golden Boot",25,text,true));
        c.addView(label(scorersIntro(),15,subText,false));
        String[][] scorers={
            {"Kylian Mbappé","France","7"},
            {"Harry Kane","England","6"},
            {"Lionel Messi","Argentina","5"},
            {"Vinícius Jr","Brazil","5"},
            {"Cristiano Ronaldo","Portugal","4"},
            {"Luka Modrić","Croatia","3"},
            {"Erling Haaland","Norway","3"},
            {"Mohamed Salah","Egypt","3"}
        };
        for(int i=0;i<scorers.length;i++){
            String[] s=scorers[i];
            c.addView(label((i+1)+". "+s[0]+" • "+flag(s[1])+" "+s[1]+" • "+s[2]+" goals",15,i<3?RED:subText,i<3));
        }
    }

    void showMvp(){
        clear(tr("MVP Prediction"),tr("Player of the tournament"),"More");
        LinearLayout c=card();
        c.setBackground(gradient(RED_DARK,RED,dp(24)));
        c.addView(label("⭐ MVP Prediction",26,Color.WHITE,true));
        c.addView(label(flag(myTeam)+" "+myTeam+" star player",23,GOLD,true));
        c.addView(label(mvpIntro(),15,Color.WHITE,false));
        LinearLayout list=card();
        list.addView(label(tr("Shortlist"),22,text,true));
        list.addView(label("1. "+flag(myTeam)+" "+myTeam+" leader\n2. 🇫🇷 France star\n3. 🇧🇷 Brazil star\n4. 🏴 England star\n5. 🇦🇷 Argentina star",15,subText,false));
    }

    void showCompareTeams() {
        clear(tr("Compare Teams"), tr("Team vs Team"), "More");
        LinearLayout c = card();
        c.addView(label("⚔️ " + tr("Compare Teams"), 25, text, true));
        Spinner a = spinner(data.teams, myTeam);
        Spinner b = spinner(data.teams, "Brazil");
        c.addView(label("Team A", 13, subText, true)); c.addView(a);
        c.addView(label("Team B", 13, subText, true)); c.addView(b);
        Button compare = btn(tr("Compare Teams"));
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
        clear(tr("Export Backup"), tr("Export / Import prediction"), "More");
        LinearLayout c = card();
        c.addView(label("💾 " + tr("Export Backup"), 24, text, true));
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
        clear(tr("PDF Poster Info"), tr("Printable prediction poster"), "More");
        LinearLayout c = card();
        c.addView(label("🖨️ " + tr("PDF Poster Info"), 24, text, true));
        c.addView(label("The app already has the poster layout. Next technical step is Android PDF export. For now, Share Poster creates a clean shareable prediction text/card.", 15, subText, false));
        Button open = btn("Open Share Poster");
        open.setOnClickListener(v -> showPoster());
        c.addView(open);
    }

    void showQuiz(){
        clear(tr("World Cup Quiz"),tr("Take quiz"),"More");
        final int[] score=new int[]{0};
        TextView scoreView=label(tr("Score")+": 0/5",22,RED,true);
        LinearLayout c=card();
        c.addView(label("🧠 "+tr("World Cup Quiz"),26,text,true));
        c.addView(scoreView);

        String[][] q={
            {t2("How many teams play in 2026?","Koliko reprezentacija igra 2026?","Wie viele Teams spielen 2026?","¿Cuántos equipos juegan en 2026?","Combien d'équipes jouent en 2026?"),"48","32","40","64","48"},
            {t2("How many groups are there?","Koliko ima skupina?","Wie viele Gruppen gibt es?","¿Cuántos grupos hay?","Combien de groupes y a-t-il?"),"12","8","10","16","12"},
            {t2("How many teams are in each group?","Koliko je reprezentacija u svakoj skupini?","Wie viele Teams sind in jeder Gruppe?","¿Cuántos equipos hay en cada grupo?","Combien d'équipes par groupe?"),"4","3","4","5","6"},
            {t2("Which group is Croatia in?","U kojoj je skupini Hrvatska?","In welcher Gruppe ist Kroatien?","¿En qué grupo está Croacia?","Dans quel groupe est la Croatie?"),"L","A","D","L","H"},
            {t2("How many third-placed teams go through?","Koliko trećeplasiranih reprezentacija prolazi dalje?","Wie viele Drittplatzierte kommen weiter?","¿Cuántos terceros pasan?","Combien de troisièmes passent?"),"8","4","6","8","10"}
        };

        for(String[] item:q){
            LinearLayout qc=card();
            qc.addView(label("❓ "+item[0],18,text,true));
            qc.addView(label(tr("Choose answer"),14,subText,false));
            final TextView result=label("",16,RED,true);
            for(int i=2;i<6;i++){
                Button a=btn(item[i]);
                final String chosen=item[i];
                final String correct=item[1];
                a.setOnClickListener(v->{
                    if(result.getText().length()>0)return;
                    if(chosen.equals(correct)){
                        score[0]++;
                        result.setText("✅ "+tr("Correct")+"! "+correct);
                    }else{
                        result.setText("❌ "+tr("Wrong")+"! "+chosen+"  → ✅ "+correct);
                    }
                    scoreView.setText(tr("Score")+": "+score[0]+"/5");
                });
                qc.addView(a);
            }
            qc.addView(result);
        }

        Button reset=btn("🏆 "+tr("Try again"));
        reset.setOnClickListener(v->showQuiz());
        c.addView(reset);
    }

    void showStadiumGuide() {
        clear(tr("Stadium Guide"), tr("Host stadium notes"), "More");
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
        clear(tr("City Guide Pro"), tr("Fan travel notes"), "More");
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
        clear(tr("Achievements"), tr("Badges and milestones"), "More");
        LinearLayout c = card();
        c.addView(label("🏅 " + tr("Achievements"), 25, text, true));
        int played = data.playedCount();
        c.addView(label((played>=1?"✅":"⬜") + " First Prediction", 17, played>=1?GREEN:subText, true));
        c.addView(label((played>=10?"✅":"⬜") + " Group Stage Analyst", 17, played>=10?GREEN:subText, true));
        c.addView(label((played>=24?"✅":"⬜") + " Tournament Expert", 17, played>=24?GREEN:subText, true));
        c.addView(label((data.progressPercent()>=100?"✅":"⬜") + " Prediction Master", 17, data.progressPercent()>=100?GOLD:subText, true));
        c.addView(label("My Team badge: " + flag(myTeam) + " " + myTeam + " Fan", 17, RED, true));
    }


    void showHostCityDetails(){
        clear(tr("Host Cities"),tr("16 host cities"),"More");
        String[][] cities={
            {"🇺🇸","Atlanta","Atlanta Stadium","United States","71,000","8","Modern host city, huge airport hub and strong sports culture.","Moderan grad domaćin, veliki zračni čvor i jaka sportska kultura."},
            {"🇺🇸","Boston","Boston Stadium","United States","65,000","7","Historic sports market with passionate East Coast fans.","Povijesni sportski grad s vatrenim navijačima istočne obale."},
            {"🇨🇦","Toronto","Toronto Stadium","Canada","45,000","6","Canada's biggest city and a multicultural football hub.","Najveći kanadski grad i multikulturalno nogometno središte."},
            {"🇺🇸","Dallas","Dallas Stadium","United States","80,000","9","One of the biggest venues of the tournament.","Jedan od najvećih stadiona turnira."}
        };
        for(String[] x:cities){
            LinearLayout c=card();
            c.addView(label(x[0]+" "+x[1],26,text,true));
            c.addView(label("🏟️ "+x[2],16,RED,true));
            c.addView(label(tr("Country")+": "+x[3]+"  •  "+tr("Capacity")+": "+x[4]+"  •  "+tr("Matches")+": "+x[5],14,subText,false));
            c.addView(label(lang.equals("HR")?x[7]:x[6],15,subText,false));
        }
    }

    void showTeamHubPro() {
        clear("Team Hub Pro", "Detailed national team view", "More");
        LinearLayout top=card();
        top.addView(label(flag(myTeam)+" "+myTeam,30,RED,true));
        top.addView(label("Group "+groupOfTeam(myTeam)+" • saved My Team • Road to Final ready",16,subText,false));
        top.addView(label("Power rating: "+teamPower(myTeam)+"/100",18,GREEN,true));
        LinearLayout games=card();
        games.addView(label("⚽ Matches",23,text,true));
        for(Match m:data.matches){
            if(m.home.equals(myTeam)||m.away.equals(myTeam)){
                games.addView(label(m.date+" • "+flag(m.home)+" "+m.home+" vs "+flag(m.away)+" "+m.away+" • "+m.venue,14,subText,false));
            }
        }
        LinearLayout notes=card();
        notes.addView(label("🔎 Fan notes",22,text,true));
        notes.addView(label("Use this screen later for squad, coach, form, star player and qualification story. It is now ready as the Team Hub base.",15,subText,false));
    }

    void showOnboardingPreview() {
        clear("Onboarding", "First launch preview", "More");
        LinearLayout a=card(); a.setBackground(gradient(RED_DARK, RED, dp(24)));
        a.addView(label("1/3  Pick your team",24,Color.WHITE,true));
        a.addView(label("Choose your national team and follow the road to the final.",16,Color.WHITE,false));
        LinearLayout b=card(); b.addView(label("2/3  Predict results",24,text,true));
        b.addView(label("Enter scores, calculate standings and build the knockout path.",16,subText,false));
        LinearLayout c=card(); c.addView(label("3/3  Share your poster",24,text,true));
        c.addView(label("Create a fan prediction and share it with friends before every matchday.",16,subText,false));
    }

    void showShareImagePlan() {
        clear("Poster Export", "Image/PDF export plan", "More");
        LinearLayout c=card();
        c.addView(label("🖼️ Share Poster Pro",24,text,true));
        c.addView(label("Current version shares prediction text/card. Next native step is PNG generation from the poster view, then PDF export for printable brackets.",15,subText,false));
        Button open=btn("Open Share Poster"); open.setOnClickListener(v->showPoster()); c.addView(open);
        LinearLayout roadmap=card();
        roadmap.addView(label("Export roadmap",22,RED,true));
        roadmap.addView(label("1. Poster preview\n2. Render as bitmap\n3. Save PNG\n4. Android share sheet\n5. PDF bracket export",15,subText,false));
    }

    void showKnockoutRules() {
        clear("Tournament Rules", "48-team format explained", "More");
        LinearLayout c=card();
        c.addView(label("📘 2026 format",24,text,true));
        c.addView(label("48 teams play in 12 groups of four. The top two teams from each group advance, plus the eight best third-placed teams. The knockout stage starts with a Round of 32.",16,subText,false));
        LinearLayout r=card();
        r.addView(label("App logic",22,RED,true));
        r.addView(label("The app calculates group tables from your scores, ranks third-placed teams and builds a predicted knockout path.",15,subText,false));
    }

    void showMonetizationPlan() {
        clear("Monetization", "Free vs Pro plan", "More");
        LinearLayout c=card(); c.setBackground(gradient(RED_DARK, RED, dp(24)));
        c.addView(label("💎 Pro plan",28,Color.WHITE,true));
        c.addView(label("Suggested: Free app + Pro unlock (€1.99–€4.99).",17,GOLD,true));
        c.addView(label("Free: scores, tables, basic prediction.\nPro: multiple predictions, poster export, PDF bracket, custom tournaments, achievements, no ads.",16,Color.WHITE,false));
        LinearLayout n=card();
        n.addView(label("Commercial focus",22,text,true));
        n.addView(label("The strongest paid feature is shareable prediction content: fans want to show their champion, final, bracket and team path.",15,subText,false));
    }


    String cityDesc(String en, String hr, String de, String es, String fr){
        if(lang.equals("HR"))return hr;
        if(lang.equals("DE"))return de;
        if(lang.equals("ES"))return es;
        if(lang.equals("FR"))return fr;
        return en;
    }
    String playerIntro(){
        if(lang.equals("HR"))return "Centar za Zlatnu kopačku, MVP prognozu i glavne zvijezde turnira.";
        if(lang.equals("DE"))return "Bereich für Goldener Schuh, MVP-Prognose und Turnierstars.";
        if(lang.equals("ES"))return "Sección para Bota de Oro, predicción MVP y estrellas del torneo.";
        if(lang.equals("FR"))return "Section Soulier d'Or, pronostic MVP et stars du tournoi.";
        return "Players hub for Golden Boot, MVP prediction and star-player watch.";
    }
    String scorersIntro(){
        if(lang.equals("HR"))return "Navijačka prognoza najboljih strijelaca. Kasnije se može povezati sa stvarnom statistikom utakmica.";
        if(lang.equals("DE"))return "Fan-Prognose der besten Torschützen. Später kann sie mit echten Spielstatistiken verbunden werden.";
        if(lang.equals("ES"))return "Predicción de goleadores para fans. Luego se puede conectar con estadísticas reales.";
        if(lang.equals("FR"))return "Pronostic des meilleurs buteurs. Plus tard, il pourra être relié aux vraies statistiques.";
        return "Fan prediction list for top scorers. Later this can be connected to real match statistics.";
    }
    String mvpIntro(){
        if(lang.equals("HR"))return "MVP prognoza prati tvoju odabranu reprezentaciju i trenutni navijački kostur.";
        if(lang.equals("DE"))return "Die MVP-Prognose folgt deinem gewählten Team und deinem aktuellen Turnierbaum.";
        if(lang.equals("ES"))return "La predicción MVP sigue tu selección elegida y tu cuadro actual.";
        if(lang.equals("FR"))return "Le pronostic MVP suit ton équipe choisie et ton tableau actuel.";
        return "MVP prediction follows your selected team and current fan bracket.";
    }

void showPlayersHub(){
        clear(tr("Players"),tr("Golden Boot"),"More");
        LinearLayout c=card();
        c.addView(label("⚽ "+tr("Players"),24,text,true));
        c.addView(label(t2("Golden Boot, MVP prediction and tournament star center.","Centar za Zlatnu kopačku, MVP prognozu i glavne zvijezde turnira.","Zentrum für Goldenen Schuh, MVP und Stars.","Centro de Bota de Oro, MVP y estrellas.","Centre Soulier d'or, MVP et stars."),15,subText,false));
        Button scorers=btn(tr("Golden Boot")); scorers.setOnClickListener(v->showScorers()); c.addView(scorers);
        Button mvp=btn(tr("MVP Prediction")); mvp.setOnClickListener(v->showMvp()); c.addView(mvp);

        LinearLayout stars=card();
        stars.addView(label("⭐ "+t2("Tournament stars","Zvijezde turnira","Turnierstars","Estrellas del torneo","Stars du tournoi"),22,text,true));
        String[][] players={
            {"Kylian Mbappé","France","Forward","27","Real Madrid"},
            {"Lionel Messi","Argentina","Forward","39","Inter Miami"},
            {"Harry Kane","England","Forward","32","Bayern Munich"},
            {"Cristiano Ronaldo","Portugal","Forward","41","Al Nassr"},
            {"Vinícius Jr","Brazil","Forward","25","Real Madrid"},
            {"Luka Modrić","Croatia","Midfielder","40","AC Milan"},
            {"Erling Haaland","Norway","Forward","25","Manchester City"},
            {"Mohamed Salah","Egypt","Forward","34","Liverpool"}
        };
        for(String[] p:players){
            stars.addView(label(flag(p[1])+" "+p[0]+" • "+p[1],17,text,true));
            stars.addView(label(tr("Position")+": "+p[2]+"  •  "+tr("Age")+": "+p[3]+"  •  "+tr("Club")+": "+p[4],14,subText,false));
        }
    }

    void showAboutApp(){
        clear(tr("About App"), tr("Independent fan-made app"), "More");
        LinearLayout c=card();
        c.addView(label("⚽ World Cup Fan 2026",26,text,true));
        c.addView(label(tr("Independent fan-made app"),18,RED,true));
        c.addView(label(tr("App version")+": 13.8",16,subText,false));
        c.addView(label("Offline predictor • Groups • Bracket • My Team • Share Poster",15,subText,false));
        LinearLayout l=card();
        l.addView(label("Legal",22,text,true));
        l.addView(label(tr("This app is not affiliated with FIFA."),15,subText,false));
        l.addView(label("No official FIFA logo, no official crests, no player photos, no live streaming.",15,subText,false));
    }

    void showPrivacyPolicy(){
        clear(tr("Privacy Policy"), tr("No login. No account. No personal profile."), "More");
        LinearLayout c=card();
        c.addView(label("🔒 Privacy Policy",26,text,true));
        c.addView(label(tr("No login. No account. No personal profile."),16,subText,false));
        c.addView(label(tr("Data is stored only on this device."),16,subText,false));
        c.addView(label(tr("Scores, language, theme and selected team are saved locally."),16,subText,false));
        LinearLayout legal=card();
        legal.addView(label("Legal safety",22,RED,true));
        legal.addView(label(tr("This app is not affiliated with FIFA."),15,subText,false));
        legal.addView(label("The app does not provide betting, live streaming or official tournament media.",15,subText,false));
    }

void showMore(){
        clear(tr("More"),tr("Language")+" • Premium • Settings","More");

        LinearLayout langCard=card();
        langCard.addView(label("🌍 Language",22,text,true));
        LinearLayout langs=hrow();
        for(String l:new String[]{"EN","HR","DE","ES","FR"}){
            TextView cc=chip(l);
            if(l.equals(lang)){
                cc.setTextColor(Color.WHITE);
                cc.setBackground(gradient(RED_DARK,RED,dp(18)));
            }
            cc.setOnClickListener(v->{
                lang=((TextView)v).getText().toString();
                prefs.edit().putString("lang",lang).apply();
                redraw();
            });
            langs.addView(cc,new LinearLayout.LayoutParams(0,dp(44),1));
        }
        langCard.addView(langs);

        LinearLayout c=card();
        Button myTeamBtn=btn(tr("My Team")); myTeamBtn.setOnClickListener(v->showMyTeam()); c.addView(myTeamBtn);
        Button citiesBtn=btn(tr("Host Cities")); citiesBtn.setOnClickListener(v->showHostCityDetails()); c.addView(citiesBtn);
        Button playersBtn=btn(tr("Players")); playersBtn.setOnClickListener(v->showPlayersHub()); c.addView(playersBtn);
        Button statsBtn=btn(tr("Statistics")); statsBtn.setOnClickListener(v->showStats()); c.addView(statsBtn);
        Button achBtn=btn(tr("Achievements")); achBtn.setOnClickListener(v->showAchievements()); c.addView(achBtn);
        Button bracketBtn=btn(tr("Visual Bracket")); bracketBtn.setOnClickListener(v->showVisualBracket()); c.addView(bracketBtn);
        Button compareBtn=btn(tr("Compare Teams")); compareBtn.setOnClickListener(v->showCompareTeams()); c.addView(compareBtn);
        Button quizBtn=btn(tr("World Cup Quiz")); quizBtn.setOnClickListener(v->showQuiz()); c.addView(quizBtn);
        Button premiumBtn=btn(tr("Premium")); premiumBtn.setOnClickListener(v->showPremium()); c.addView(premiumBtn);
        Button privacyBtn=btn(tr("Privacy / Legal")); privacyBtn.setOnClickListener(v->showPrivacyInfo()); c.addView(privacyBtn);

                Button aboutBtn=btn(tr("About App")); aboutBtn.setOnClickListener(v->showAboutApp()); c.addView(aboutBtn);
        Button privacyPolicyBtn=btn(tr("Privacy Policy")); privacyPolicyBtn.setOnClickListener(v->showPrivacyPolicy()); c.addView(privacyPolicyBtn);
Button mode=btn(darkMode?tr("Switch to Light Mode"):tr("Switch to Dark Mode"));
        mode.setOnClickListener(v->{
            darkMode=!darkMode;
            prefs.edit().putBoolean("darkMode",darkMode).apply();
            redraw();
        });
        c.addView(mode);

        LinearLayout l=card();
        l.addView(label("Version 13.10 Build Verified",22,RED,false));
        l.addView(label(tr("No official FIFA logo, no official crests, no player photos, no live streaming."),15,subText,false));
    }

    void showPremium(){
        clear(tr("Premium"),t2("Free vs Pro","Besplatno vs Pro","Kostenlos vs Pro","Gratis vs Pro","Gratuit vs Pro"),"More");
        LinearLayout c=card();
        c.setBackground(gradient(RED_DARK,RED,dp(24)));
        c.addView(label("💎 World Cup Fan Pro",27,Color.WHITE,true));
        c.addView(label(tr("Free").toUpperCase(),18,Color.WHITE,true));
        c.addView(label("• "+tr("Basic scores")+"\n• "+tr("Group tables")+"\n• "+tr("One prediction"),16,Color.WHITE,false));
        c.addView(label("PRO",18,Color.WHITE,true));
        c.addView(label("• "+tr("Multiple saved predictions")+"\n• "+tr("Share poster")+"\n• "+tr("Advanced statistics")+"\n• "+tr("Dream finals")+"\n• "+tr("Premium themes")+"\n• "+tr("Export PDF/PNG")+"\n• "+tr("Match reminders")+"\n• "+tr("Custom tournaments"),16,Color.WHITE,false));
    }
    void showStats(){clear(tr("Statistics"),"Advanced insights","More");LinearLayout c=card();c.addView(label("📊 Statistics",25,text,true));c.addView(kpiRow("Played",""+data.playedCount(),"Goals",""+data.totalGoals(),"Progress",data.progressPercent()+"%",false));c.addView(label("Average goals: "+data.avgGoals(),18,subText,false));c.addView(label("Best attack: "+data.bestAttack(),18,RED,true));c.addView(label("Highest scoring group: "+data.bestGroup(),18,GREEN,true));}
    void sharePrediction(){shareText("⚽ World Cup Fan 2026\n\nMy Team: "+flag(myTeam)+" "+myTeam+"\nChampion pick: "+topInline(1)+"\nTop 4: "+topInline(4)+"\nProgress: "+data.progressPercent()+"%\n\nWorld Cup Fan 2026");}
    void shareText(String msg){Intent send=new Intent(Intent.ACTION_SEND);send.setType("text/plain");send.putExtra(Intent.EXTRA_TEXT,msg);startActivity(Intent.createChooser(send,"Share"));hideSystemBars();}
    
    void showPlayStoreChecklist() {
        clear("Play Store", tr("Launch readiness checklist"), "More");
        LinearLayout c = card();
        c.addView(label(" Play Store Launch Checklist", 24, text, true));
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
        clear(tr("Privacy"), tr("Legal safety"), "More");
        LinearLayout c = card();
        c.addView(label("🔒 " + tr("Privacy"), 24, text, true));
        c.addView(label("World Cup Fan 2026 works offline. Scores, selected team, language and theme are stored only on this device. No login, no personal profile and no official football data ownership is claimed.", 15, subText, false));
        LinearLayout l = card();
        l.addView(label("Legal safety", 22, RED, true));
        l.addView(label("Independent fan-made app. No official FIFA logo, no official crests, no player photos, no streaming and no betting services.", 15, subText, false));
    }

void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}

    String flag(String team){
        if(team==null)return "🏳️";
        if(team.equals("England"))return "🏴";
        if(team.equals("Scotland"))return "🏴";
        if(team.equals("Wales"))return "🏴";
        if(team.equals("Croatia"))return "🇭🇷";
        if(team.equals("Brazil"))return "🇧🇷";
        if(team.equals("Argentina"))return "🇦🇷";
        if(team.equals("France"))return "🇫🇷";
        if(team.equals("Germany"))return "🇩🇪";
        if(team.equals("Spain"))return "🇪🇸";
        if(team.equals("Portugal"))return "🇵🇹";
        if(team.equals("Netherlands"))return "🇳🇱";
        if(team.equals("Belgium"))return "🇧🇪";
        if(team.equals("Mexico"))return "🇲🇽";
        if(team.equals("Canada"))return "🇨🇦";
        if(team.equals("USA"))return "🇺🇸";
        if(team.equals("South Africa"))return "🇿🇦";
        if(team.equals("Korea Republic"))return "🇰🇷";
        if(team.equals("Czechia"))return "🇨🇿";
        if(team.equals("Bosnia and Herzegovina"))return "🇧🇦";
        if(team.equals("Qatar"))return "🇶🇦";
        if(team.equals("Switzerland"))return "🇨🇭";
        if(team.equals("Morocco"))return "🇲🇦";
        if(team.equals("Haiti"))return "🇭🇹";
        if(team.equals("Paraguay"))return "🇵🇾";
        if(team.equals("Australia"))return "🇦🇺";
        if(team.equals("Turkey"))return "🇹🇷";
        if(team.equals("Curaçao"))return "🇨🇼";
        if(team.equals("Cote d'Ivoire"))return "🇨🇮";
        if(team.equals("Ecuador"))return "🇪🇨";
        if(team.equals("Japan"))return "🇯🇵";
        if(team.equals("Sweden"))return "🇸🇪";
        if(team.equals("Tunisia"))return "🇹🇳";
        if(team.equals("Egypt"))return "🇪🇬";
        if(team.equals("Iran"))return "🇮🇷";
        if(team.equals("New Zealand"))return "🇳🇿";
        if(team.equals("Cape Verde"))return "🇨🇻";
        if(team.equals("Saudi Arabia"))return "🇸🇦";
        if(team.equals("Uruguay"))return "🇺🇾";
        if(team.equals("Senegal"))return "🇸🇳";
        if(team.equals("Iraq"))return "🇮🇶";
        if(team.equals("Norway"))return "🇳🇴";
        if(team.equals("Algeria"))return "🇩🇿";
        if(team.equals("Austria"))return "🇦🇹";
        if(team.equals("Jordan"))return "🇯🇴";
        if(team.equals("DR Congo"))return "🇨🇩";
        if(team.equals("Uzbekistan"))return "🇺🇿";
        if(team.equals("Colombia"))return "🇨🇴";
        if(team.equals("Ghana"))return "🇬🇭";
        if(team.equals("Panama"))return "🇵🇦";
        return "🏳️";
    }
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
