package de.ws.client;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.ws.shared.Text;
import de.ws.shared.TokenizedText;
import de.ws.shared.User;

public class TextsView extends Composite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7648881305107087421L;

	// UIBinder	
	private static TextsViewUiBinder uiBinder = GWT.create(TextsViewUiBinder.class);
	interface TextsViewUiBinder extends UiBinder<Widget, TextsView> {
	}

	private final GreetingServiceAsync textsviewGreetingService = GWT.create(GreetingService.class);

	@UiField
	HTMLPanel contentPanel;
	@UiField
	Button cardPanel1;
	@UiField
	Button cardPanel2;
	@UiField
	Button cardPanel3;
	@UiField
	Button cardPanel4;
	@UiField
	Button cardPanel5;
	@UiField
	Button cardPanel6;
	@UiField
	Button cardPanel7;
	@UiField
	Button cardPanel8;
	@UiField
	Button cardPanel9;
	@UiField
	HTMLPanel row1Panel;
	@UiField
	HTMLPanel row2Panel;
	@UiField
	HTMLPanel row3Panel;
	Button cardbtn;
	HTMLPanel textRow;
	HTMLPanel container;
	HTMLPanel row;
	HTMLPanel col;
	HTMLPanel polaroid;
	HTMLPanel cardbody;
	HTML cardtitle;
	HTML cardtext;
	Anchor cardbutton;
	User user;
	String title; 
	String level;

	private HTMLPanel img;

	public TextsView() {

		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiConstructor
	public TextsView(HTMLPanel contentPanel, User user) {
		String text1 = "On tammikuu ja alennusmyyntien aika. Irina on juossut koko päivän kaupasta kauppaan. Hänellä on kädet täynnä kasseja. Nyt hänellä on kiire kotiin. Hän juoksee bussipysäkille. Yhtäkkiä hän liukastuu ja kaatuu kadulle. Kun hän yrittää nousta, hän ei pääse ylös, koska vasemmassa nilkassa on valtava kipu. Hän pyytää vieressä seisovalta mieheltä apua. Mies soittaa kännykällä ambulanssin. Kun ambulanssi tulee, Irina nostetaan autoon ja viedään sairaalan ensiapuosastolle. Sairaalassa Irina nostetaan paareille, ja sairaanhoitaja ottaa hänen henkilötietonsa. Sitten lääkäri tutkii Irinan, ja hänet viedään röntgeniin. Lääkäri kertoo Irinalle, että hänen nilkkansa ei ole murtunut, vaan nyrjähtänyt. Sairaanhoitaja laittaa Irinan nilkkaan tukisiteen. Lääkäri sanoo Irinalle, että hän voi mennä kotiin, mutta hänen täytyy levätä muutama päivä. Irina pyytää sairaanhoitajaa soittamaan hänen miehelleen. Irina on iloinen, että hänen ei tarvitse jäädä sairaalaan.";
		String title1= "In the hospital";
		String text2 = "Kaisun työvuoro ravintola Makupalassa on juuri alkanut. Hänellä on iltavuoro. Ravintolassa on vain muutama asiakas. Kello on neljä iltapäivällä. Ikkunapöydässä istuu keski-ikäinen pariskunta. Ravintolan keskellä istuu kolme nuorta naista, ja nurkkapöydässä on nuori mies yksin. Kaisu ottaa ruokalistan mukaansa, ja menee ensin pariskunnan pöytään. Hän antaa ruokalistat heille ja kysyy, mitä he haluavat juoda. Pariskunta tilaa pullon punaviiniä. Sitten Kaisu menee nuorten naisten pöytään. Tytöt tilaavat kolme lonkeroa. Lopuksi Kaisu menee nuoren miehen luo. Mies ottaa ison tuopin keskiolutta. Kaisu hakee juotavat, ja vie ne asiakkaille. Samalla hän ottaa ruokatilauksen pariskunnalta. Hän vie tilauksen kokille keittiöön. Ravintolaan tulee perhe, jossa on kaksi lasta. Toisella lapsista on syntymäpäivä. Kaikki haluavat isot jäätelöannokset ja lapset haluavat myös kokikset. Kaisu on hyvällä tuulella. Asiakkaita ei ole liikaa, ja hän ehtii palvelemaan heitä kunnolla.";
		String title2 = "Restaurant";
		String text3 = "On kaunis heinäkuun ilta. Virtasen perhe on ollut viikon mökillä. Pekka Virtasella on lomaa koko heinäkuu. Leena, hänen vaimonsa, on äitiyslomalla. Hän menee töihin vasta syyskuun alussa. Virtasella on kolme lasta: Jaakko, 7 vuotta, Sari, neljä vuotta ja Ville, kohta vuoden.\n" + 
				"Kello on viisi iltapäivällä. Isä on järvellä soutamassa. Äiti ja Sari ovat keittiössä leipomassa pullaa. Jaakko on lämmittämässä saunaa ja pikku Ville on verannalla nukkumassa. Aurinko paistaa vielä lämpimästi, taivas on sininen ja pilvetön ja järvi on aivan tyyni. Syreenipensaat ovat pihalla täydessä kukassa.\n" + 
				"Isä tulee soutamasta ja menee saunalle katsomaan, mitä Jaakko on tekemässä. Hän käy hakemassa lisää puita ja jatkaa saunan lämmittämistä, koska Jaakko haluaa mennä uimaan. Pikku Ville herää ja rupeaa itkemään. Isä ottaa Villen syliinsä ja vie hänet sisälle. Äiti ja Sari ovat kattamassa kahvipöytää, koska Leenan sisko tulee perheensä kanssa kylään. Heillä on mökki järven toisella puolella.\n" + 
				"Nyt kello on kahdeksan. Vieraat ovat jo paikalla. Miehet ovat käyneet jo saunassa. He istuvat pihalla ja juovat olutta. Naisetkin tulevat saunasta ja menevät miesten luo. Jaakko hakee kellarista makkaraa ja kaikki alkavat paistaa makkaroita.\n" + 
				"Ilma on edelleen lämmin. He nauttivat yhdessä kauniista, rauhallisesta kesäillasta.";
		String title3 = "Summer at cottage";
		String text4 = "Nykyään kuka tahansa voi ostaa matkan avaruuteen. Tarvitset vain 20 miljoonaa euroa ylimääräistä rahaa. Aiemmin suuret valtiot lähettivät raketteja avaruuteen. Nyt myös yritykset rakentavat avaruusraketteja. Yritykset aikovat myydä avaruuslentoja kuin lomamatkoja. Kuuluisin avaruusyritys on amerikkalainen SpaceX. Sen omistaa upporikas Elon Musk. SpaceX on valmistanut uudenlaisen raketin nimeltään Falcon Heavy. Se vie avaruusaluksia avaruuteen. Uutta on se, että osia Falcon Heavy -raketista palaa ehjinä maahan. Niitä voi siis käyttää monta kertaa uudelleen. Siksi lennot avaruuteen halpenevat. Mutta kallista se on silti. Yksi lento maksaa kymmeniä miljoonia euroja. Siksi Falcon Heavy on tarkoitettu myös yrityksille, jotka vievät avaruuteen mittauslaitteita tai satelliitteja. Toinen yrittäjä on amerikkalainen Richard Branson. Hänen yrityksensä Virgin Galactic valmistelee ensimmäistä matkaa avaruusturisteille." 
				+ "Myös venäläinen avaruusyritys Energia aikoo viedä matkailijoita avaruuteen. Avaruuteen pääsee 20–40 miljoonalla eurolla. Jos matkailija haluaa kävelylle avaruuteen, hinta nousee 80 miljoonaan euroon.";
		String title4 = "Vacation in Space";
		String text5 = "Suomi on valittu maailman onnellisimmaksi maaksi. Valinta perustuu YK:n julkaisemaan raporttiin, jossa on mukana 156 maata.\n" + 
				"Kansainvälinen World Happiness Report -onnellisuustutkimus julkaistiin viime viikolla. Tutkimuksen mukaan Suomi on noussut maailman onnellisimmaksi maaksi. Pohjoismaat ovat sijoittuneet onnellisuuslistalla kärkisijoille aina siitä lähtien, kun sitä alettiin julkaista vuonna 2012. Suomi ei ole kuitenkaan koskaan aiemmin voittanut listausta. Suomalaisten jälkeen listalla ovat esimerkiksi islantilaiset, norjalaiset, sveitsiläiset ja tanskalaiset.\n" + 
				"Tänä vuonna onnellisuusraporttiin otettiin mukaan ensimmäistä kertaa myös maahanmuuttajat. Suomi on paras maa myös maahanmuuttajille. Maahanmuuttajien onnellisuuden suurin tekijä uudessa maassa on elämänlaatu. Raportti ei vertaile ainoastaan kansalaisten onnellisuutta, vaan sijoitukseen vaikuttaa myös esimerkiksi korruption vähäisyys.\n" +
				"Maailma viettää tänään tiistaina 20. maaliskuuta YK:n julistamaa onnellisuuden päivää.";
		String title5 = "The happy country";
		String text6 = "Saunayhdistykset haluavat saada suomalaisen saunan Unescon maailmanperintö-luetteloon.\n" + 
				"Unesco on YK:n eli Yhdistyneiden kansakuntien järjestö, joka edistää kasvatusta, tiedettä ja kulttuuria koko maailmassa. Unescon maailmanperintö-luettelossa luetellaan eri maiden arvokasta kulttuuriperintöä. Luettelossa on esimerkiksi perinteitä, tansseja, seremoinoita, leikkejä, lauluja ja ruokalajeja.\n" + 
				"Ensi vuonna Suomi aikoo ehdottaa omaa kulttuuriperintöään Unescon luetteloon. Ehdotuksesta päättää opetus- ja kulttuuriministeriö. Suomen ehdotus valitaan suomalaisten omasta luettelosta.\n" + 
				"Suomalaisten luettelossa on 50 erilaista suomalaista perinnettä ja tapaa, esimerkiksi tällaisia perinteitä: joulukynttilän sytyttäminen haudoille, pääsiäiskokko, tervanpoltto, suomalainen tango, romanien lauluperinne ja latotanssit.\n" + 
				"Saunayhdistysten kampanja\n" + 
				"Saunayhdistykset ovat aloittaneet kampanjan, jonka tavoitteena on saada saunominen kansainväliseen Unescon luetteloon. Yhdistykset järjestävät ympäri Suomea erilaisia tapahtumia, joilla tuetaan kampanjaa.\n" + 
				"– Kampanjassa ovat mukana saunayhdistykset Helsingistä Lappiin Sallaan asti. Me haluamme vahvistaa suomalaisten saunaperinnettä, sanoo Suomen saunaseuran varapuheenjohtaja Ritva Ohmeroluoma.\n" + 
				"– Saunominen on perinne, joka yhdistää meitä kaikkia. Me rakastamme saunomista. Toivottavasti kaikki suomalaiset auttavat saamaan suomalaisen saunan Unescon luetteloon, jatkaa Ohmeroluoma.";
		String title6 = "Finnish Sauna";
		String text7 = "Keskiajalla, niin sanotun toisen ristiretken jälkeen, ruotsalaisten kolonisaatio ulottui myös uudellemaalle. 1630-luvulla esitetyn käsityksen mukaan Helsingin seudun siirtolaiset olisivat tulleet 1200-luvun puolivälin tienoilla Keski-Ruotsista Hälsinglandin maakunnasta, ja Vantaanjokea olisi ryhdytty uudisasukkaiden mukaan kutsumaan nimellä Helsingå, mistä 1300-luvulla syntynyt kirkkopitäjä olisi vuorostaan saanut nimen Helsinge. Nykyisin käsitystä pidetään kuitenkin kyseenalaisena, sillä murretutkimuksen perusteella uudisasukkaat tulivat Uplannista ja sen lähiseuduilta. Historioitsija Tapio Salminen arvioi, että nimi Helsinge juontuu ”pikemminkin jostain itse seutuun ja sen kolonisaatioasutuksen sijaintiin liittyvästä piirteestä” eikä nimen alkuperä ole enää yksiselitteisesti tutkimuksen tavoitettavissa.\n" + 
				"Kun joen suulle, Forsbyn kylän (nykyisin Koskela) kohdalle, alettiin perustaa kaupunkia vuonna 1548, siitä ryhdyttiin käyttämään nimitystä Helsinge fors eli ’Helsingin koski’, mikä vakiintui sitten muotoon Helsingfors. Nimi viittaa Vantaanjoen suulla sijaitsevaan koskeen, joka nykyään tunnetaan nimellä Vanhankaupunginkoski. Kansan suussa paikka tunnettiin kuitenkin lähinnä muunnelmilla Helsinge tai Helsing, josta kehittyi suomenkielinen muoto Helsinki.\n" + 
				"Nimeä Helsinki on käytetty suomenkielisissä asiakirjoissa ja sanomalehdissä vuodesta 1819 lähtien, jolloin Suomen senaatti siirtyi kaupunkiin ja siellä annetut asetukset alettiin päivätä siellä. Näin nimi Helsinki vakiintui suomen kirjakieleen.";
		String title7 = "Helsinki";
		String text8 = "Suomi oli 1920-luvun lopulta 1980-luvulle eräs Euroopan heikoimmista jalkapallomaista. 1990-luvulta alkaen Suomi on noussut maanosan alempaan keskikastiin, toisaalta koska ulkomaiden tasokkaissa sarjoissa pelaavien suomalaisammattilaisten määrä on kasvanut muun muassa vapaampien EU-säännösten vuoksi, toisaalta koska Euroopan jalkapalloon on tullut lukuisia uusia pienmaita. Suomi ei ole koskaan selviytynyt miesten puolella MM- tai EM-turnaukseen, mutta olympiaturnaukseen kuitenkin muutaman kerran. Suomi on Baltian maiden, Itävallan ja Irlannin ohella Euroopan ainoa maa, jossa jalkapallo ei ole ykköslaji.[58] Jalkapallon imagon on sanottu viime vuosina parantuneen, mutta pääsarjan eli Veikkausliigan suosio on esimerkiksi muiden Pohjoismaiden pääsarjoihin verrattaessa vaatimaton. Silti Suomesta on tullut eräitä huippujalkapalloilijoita, kuten Jari Litmanen ja Sami Hyypiä. Nykyisiä huippupelaajia ovat muun muassa Roman Eremenko, Teemu Pukki ja Niklas Moisander. Suomen naiset selvisivät vuoden 2005 EM-turnaukseen ja etenivät välieriin saakka. Suomen jalkapallomaajoukkue toimii Suomen Palloliiton alaisuudessa.";
		String title8 = "Finnish Football";
		String text9 = "Yhtiön perusti Ingvar Kamprad 17-vuotiaana vuonna 1943. Nimi on yhdistelmä Ingvarin nimikirjaimista sekä hänen syntytalostaan ja -kylästään: Ingvar Kamprad Elmtaryd Agunnaryd. Alun perin se myi kyniä, lompakoita, valokuvakehyksiä, kelloja ja nylonsukkia sekä melkein mitä tahansa, mitä Kamprad kykeni myymään tavallista halvemmalla hinnalla.\n" + 
				"Alun perin Kamprad myi huonekalujaan kotoaan ja postimyynnillä mutta avasi lopulta vuonna 1958 läheiseen Älmhultin kylään myymälän. Ensimmäiset huonekalut ilmestyivät valikoimiin vuonna 1948, ja vuonna 1955 Ikea alkoi suunnitella omia huonekalujaan. Puolasta alkoi 1960-luvulla muodostua yksi Ikea-tuotteiden tärkeimmistä tuottajamaista. Vuonna 1965 avattiin ensimmäinen tavaratalo Tukholmassa ja vuonna 1963 ensimmäinen myymälä ulkomailla, Oslossa Norjassa. Vuonna 1985 avattiin ensimmäinen tavaratalo Yhdysvalloissa, Philadelphiassa. Vuonna 1997 avattiin yhtiön verkkosivusto, ja vuodesta 2000 lähtien tuotteita sai ostaa yhtiön nettikaupasta.\n" + 
				"2000-luvulla yritys on panostanut voimakkaasti esimerkiksi Venäjän markkinoihin, jossa myynti onkin kasvanut voimakkaasti. Myös tammikuussa 2015 Ikea kertoi vahvistavansa sekä myymäläverkkoaan että tuotantoaan Venäjällä maan Ukrainaan kohdistamista sotatoimista seuranneista talouspakotteista huolimatta. Kalustetuotannon ohella esimerkiksi suuri osa yhtiön myymästä keramiikasta (astioista) on valmistettu Venäjän Tverin alueen Konakovossa, josta tuotteita viedään myös muiden maiden markkinoille.\n" + 
				"Maailman viisi suurinta Ikeaa sijaitsevat näissä paikoissa";
		String title9 = "IKEA";
		initWidget(uiBinder.createAndBindUi(this));
		this.user = user;

		this.contentPanel = contentPanel;
		this.user = user;		
		contentPanel.clear();

		cardPanel1.addDomHandler(new TextClickHandler(text1,title1, "easy", contentPanel, user), ClickEvent.getType());
		cardPanel2.addDomHandler(new TextClickHandler(text2,title2, "easy", contentPanel, user), ClickEvent.getType());
		cardPanel3.addDomHandler(new TextClickHandler(text3,title3, "easy", contentPanel, user), ClickEvent.getType());
		cardPanel1.getElement().setClassName("cardbutton");
		cardPanel2.getElement().setClassName("cardbutton");
		cardPanel3.getElement().setClassName("cardbutton");
		cardPanel3.removeStyleName("button");
		contentPanel.add(row1Panel);

		cardPanel4.addDomHandler(new TextClickHandler(text4,title4, "medium" ,contentPanel, user), ClickEvent.getType());
		cardPanel5.addDomHandler(new TextClickHandler(text5,title5, "medium" ,contentPanel, user), ClickEvent.getType());
		cardPanel6.addDomHandler(new TextClickHandler(text6,title6, "medium" ,contentPanel, user), ClickEvent.getType());
		cardPanel4.getElement().setClassName("cardbutton");
		cardPanel5.getElement().setClassName("cardbutton");
		cardPanel6.getElement().setClassName("cardbutton");
		contentPanel.add(row2Panel);

		cardPanel7.addDomHandler(new TextClickHandler(text7,title7, "hard", contentPanel, user), ClickEvent.getType());
		cardPanel8.addDomHandler(new TextClickHandler(text8,title8, "hard", contentPanel, user), ClickEvent.getType());
		cardPanel9.addDomHandler(new TextClickHandler(text9,title9, "hard", contentPanel, user), ClickEvent.getType());
		cardPanel7.getElement().setClassName("cardbutton");
		cardPanel8.getElement().setClassName("cardbutton");
		cardPanel9.getElement().setClassName("cardbutton");
		contentPanel.add(row3Panel);

		ArrayList<Text> temp = new ArrayList<>();
		textRow = new HTMLPanel("");
		container = new HTMLPanel("");
		container.setStyleName("container");
		textRow.add(container);
		for(Text t : user.getTexts()) {
			temp.add(t);
			if (temp.size() == 3) {
				row = new HTMLPanel("");
				container.add(row);
				row.setStyleName("row");
				for(Text tempText : temp) {
					col = new HTMLPanel("");
					cardbody = new HTMLPanel("");
					img = new HTMLPanel("");
					img.setStyleName("imgg");
					row.add(col);
					polaroid = new HTMLPanel("");
					col.setStyleName("col flexing");
					col.add(polaroid);
					polaroid.add(img);
					polaroid.add(cardbody);
					if(tempText.getTitle().equals("")) {
						cardtitle = new HTML("<h5 class=\"card-title\">No name</h5>");
					} else {
						cardtitle = new HTML("<h5 class=\"card-title\">" + tempText.getTitle() +"</h5>");
					}
					if(tempText.getLevel().equals("")) {
						cardtext = new HTML("<p class=\"card-text\">Reading level: No level</p>");
					} else {
						cardtext = new HTML("<p class=\"card-text\">Reading level:" + tempText.getLevel() + "</p>");
					}
					cardbody.add(cardtitle);
					cardbody.add(cardtext);

					cardbtn = new Button("");
					cardbtn.addDomHandler(new TextClickHandler(tempText.getText(),tempText.getTitle(), tempText.getLevel(), contentPanel, user), ClickEvent.getType());
					cardbtn.setStyleName("cardbutton");
					cardbtn.setHTML("<a href=\"#\" class=\"cardbutton\">Read this text</a>");
					cardbody.add(cardbtn);
					polaroid.setStyleName("polaroid");
					cardbody.setStyleName("card-body");
					cardtitle.setStyleName("card-title");
					cardtext.setStyleName("card-text");
				}
				contentPanel.add(textRow);
				temp.clear();
			}
		} 
		row = new HTMLPanel("");
		container.add(row);

		row.setStyleName("row");

		for(Text tempText : temp) {
			col = new HTMLPanel("");
			cardbody = new HTMLPanel("");
			img = new HTMLPanel("");
			img.setStyleName("imgg");
			row.add(col);
			polaroid = new HTMLPanel("");
			col.setStyleName("col flexing");
			col.add(polaroid);
			polaroid.add(img);
			polaroid.add(cardbody);

			cardtitle = new HTML("<h5 class=\"card-title\">" + tempText.getTitle() +"</h5>");
			cardbody.add(cardtitle);
			cardtext = new HTML("<p class=\"card-text\">Reading level:" + tempText.getLevel() + "medium</p>");
			cardbody.add(cardtext);

			cardbtn = new Button("");
			cardbtn.addDomHandler(new TextClickHandler(tempText.getText(),tempText.getTitle(), tempText.getLevel(), contentPanel, user), ClickEvent.getType());
			cardbtn.setStyleName("cardbutton");
			cardbtn.setHTML("<a href=\"#\" class=\"cardbutton\">Read this text</a>");
			cardbody.add(cardbtn);
			polaroid.setStyleName("polaroid");
			cardbody.setStyleName("card-body");
			cardtitle.setStyleName("card-title");
			cardtext.setStyleName("card-text");

		}
		contentPanel.add(textRow);
	}
	private class TextClickHandler implements ClickHandler {
		String text;
		HTMLPanel cp;
		User user;
		String currenttitle; 
		String currentlevel;

		public TextClickHandler(String text1, String currenttitle, String currentlevel, HTMLPanel cp, User user) {
			this.text = text1;
			this.cp = cp;
			this.user = user;
			this.currentlevel = currentlevel;
			this.currenttitle = currenttitle;

		}
		@Override
		public void onClick(ClickEvent event) {
			textsviewGreetingService.createText(text, new TokenizationCallback(text, currenttitle, currentlevel));


		}
	}
	private class TokenizationCallback implements AsyncCallback<TokenizedText> {
		String title;
		String level;
		String currenttext;
		public TokenizationCallback(String currenttext, String title, String level) {
			this.title = title;
			this.level = level;
			this.currenttext = currenttext;
		}

		@Override
		public void onSuccess(TokenizedText result) {
			//contentPanel.clear();
			Text text = new Text();
			text.setText("");
			text.setTitle(title);
			text.setLevel(level);
			text.setSaved(true);

			TranslatorView tw = new TranslatorView(result.getTokens(), result.getTranslationMap(), contentPanel, user, text);
		}

		@Override
		public void onFailure(Throwable caught) {
			Window.scrollTo(0, 0);
		}
	}

}