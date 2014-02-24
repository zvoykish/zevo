package com.zvoykish.zevo.framework.cwd;

import com.zvoykish.zevo.framework.EvoExtraConfiguration;
import com.zvoykish.zevo.utils.Logger;
import com.zvoykish.zevo.utils.Randomizer;
import com.zvoykish.zevo.utils.XMLUtils;
import org.jdom.Element;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Zvika
 * Date: 01/05/2010
 * Time: 17:14:39
 */
public class CWDExtraConfiguration extends EvoExtraConfiguration {
    private int individualsPerSpecies;
    private int[][] femaleMalePrefs;
    private int[][] femaleDogPrefs;
    private int[][] maleFemalePrefs;
    private int[][] maleDogPrefs;
    private int[][] dogFemalePrefs;
    private int[][] dogMalePrefs;

    private static final String TAG_INDIVIDUALS_PER_SPECIES = "IndividualsPerSpecies";
    private static final String TAG_PREFERENCES_FILE = "PreferencesFile";
    private static final String TAG_FEMALE_PREF = "FemalePref";
    private static final String TAG_MALE_PREF = "MalePref";
    private static final String TAG_DOG_PREF = "DogPref";
    private static final String ATTR_FEMALES = "Females";
    private static final String ATTR_MALES = "Males";
    private static final String ATTR_DOGS = "Dogs";
    private static final String TAG_PREFERENCES = "Preferences";

    @Override
    protected void parseModelExtraElement(Element modelExtraElement) {
        Logger logger = Logger.getInstance();

        String individualsCountStr = modelExtraElement.getChildTextTrim(TAG_INDIVIDUALS_PER_SPECIES);
        individualsPerSpecies = Integer.valueOf(individualsCountStr);
        initPreferencesArrays();

        String prefsFileStr = modelExtraElement.getChildTextTrim(TAG_PREFERENCES_FILE);
        if (prefsFileStr == null || prefsFileStr.trim().isEmpty()) {
            randomlyCreatePreferences();
            return;
        }

        try {
            Element prefsElement = XMLUtils.getXmlFileRootElement(prefsFileStr);

            // Parse females preferences
            List<Element> femalesPrefsElements = prefsElement.getChildren(TAG_FEMALE_PREF);
            for (int i = 0; i < femalesPrefsElements.size(); i++) {
                Element femalePrefElement = femalesPrefsElements.get(i);
                String males = femalePrefElement.getAttributeValue(ATTR_MALES);
                String dogs = femalePrefElement.getAttributeValue(ATTR_DOGS);
                fillPreferencesArray(femaleMalePrefs, femaleDogPrefs, males, dogs, i);
            }

            // Parse males preferences
            List<Element> malesPrefsElements = prefsElement.getChildren(TAG_MALE_PREF);
            for (int i = 0; i < malesPrefsElements.size(); i++) {
                Element malePrefElement = malesPrefsElements.get(i);
                String females = malePrefElement.getAttributeValue(ATTR_FEMALES);
                String dogs = malePrefElement.getAttributeValue(ATTR_DOGS);
                fillPreferencesArray(maleFemalePrefs, maleDogPrefs, females, dogs, i);
            }

            // Parse dogs preferences
            List<Element> dogsPrefsElements = prefsElement.getChildren(TAG_DOG_PREF);
            for (int i = 0; i < dogsPrefsElements.size(); i++) {
                Element dogPrefElement = dogsPrefsElements.get(i);
                String females = dogPrefElement.getAttributeValue(ATTR_FEMALES);
                String males = dogPrefElement.getAttributeValue(ATTR_MALES);
                fillPreferencesArray(dogFemalePrefs, dogMalePrefs, females, males, i);
            }
        }
        catch (Exception e) {
            String msg = "Failed opening/parsing preferences file - " + prefsFileStr;
            logger.log(msg + ", error: " + e);
            throw new RuntimeException(msg, e);
        }
    }

    private void fillPreferencesArray(int[][] prefs1, int[][] prefs2, String pref1Str, String pref2Str, int rowIndex) {
        StringTokenizer pref1Tokenizer = new StringTokenizer(pref1Str, ",");
        StringTokenizer pref2Tokenizer = new StringTokenizer(pref2Str, ",");
        int current = 0;
        while (pref1Tokenizer.hasMoreTokens()) {
            String currentPref1Str = pref1Tokenizer.nextToken();
            String currentPref2Str = pref2Tokenizer.nextToken();
            Integer currentPref1 = Integer.valueOf(currentPref1Str);
            Integer currentPref2 = Integer.valueOf(currentPref2Str);
            prefs1[rowIndex][current] = currentPref1;
            prefs2[rowIndex][current] = currentPref2;
            current++;
        }
    }

    private void initPreferencesArrays() {
        femaleMalePrefs = new int[individualsPerSpecies][individualsPerSpecies];
        femaleDogPrefs = new int[individualsPerSpecies][individualsPerSpecies];
        maleFemalePrefs = new int[individualsPerSpecies][individualsPerSpecies];
        maleDogPrefs = new int[individualsPerSpecies][individualsPerSpecies];
        dogFemalePrefs = new int[individualsPerSpecies][individualsPerSpecies];
        dogMalePrefs = new int[individualsPerSpecies][individualsPerSpecies];
    }

    private void randomlyCreatePreferences() {
        for (int i = 0; i < individualsPerSpecies; i++) {
            List<Integer> femalesMales = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
            List<Integer> femalesDogs = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
            List<Integer> malesFemales = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
            List<Integer> malesDogs = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
            List<Integer> dogsFemales = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
            List<Integer> dogsMales = Randomizer.randomize(individualsPerSpecies, 0, individualsPerSpecies - 1);
            for (int j = 0; j < individualsPerSpecies; j++) {
                femaleMalePrefs[i][j] = femalesMales.get(j);
                femaleDogPrefs[i][j] = femalesDogs.get(j);
                maleFemalePrefs[i][j] = malesFemales.get(j);
                maleDogPrefs[i][j] = malesDogs.get(j);
                dogFemalePrefs[i][j] = dogsFemales.get(j);
                dogMalePrefs[i][j] = dogsMales.get(j);
            }
        }

        writePreferencesToFile();
    }

    private void writePreferencesToFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd__HHmm_ss").format(new Date());
        Element prefsElement = new Element(TAG_PREFERENCES);

        for (int i = 0; i < femaleMalePrefs.length; i++) {
            StringBuilder femaleMaleBuilder = new StringBuilder();
            StringBuilder femaleDogBuilder = new StringBuilder();
            StringBuilder maleFemaleBuilder = new StringBuilder();
            StringBuilder maleDogBuilder = new StringBuilder();
            StringBuilder dogFemaleBuilder = new StringBuilder();
            StringBuilder dogMaleBuilder = new StringBuilder();

            // Add Female->Male/Dog preferences
            int[] femaleIMalePrefs = femaleMalePrefs[i];
            int[] femaleIDogPrefs = femaleDogPrefs[i];
            for (int j = 0; j < femaleIMalePrefs.length; j++) {
                femaleMaleBuilder.append(femaleIMalePrefs[j]);
                if (j < femaleIMalePrefs.length - 1) {
                    femaleMaleBuilder.append(',');
                }

                femaleDogBuilder.append(femaleIDogPrefs[j]);
                if (j < femaleIDogPrefs.length - 1) {
                    femaleDogBuilder.append(',');
                }
            }

            // Add Male->Female/Dog preferences
            int[] maleIFemalePrefs = maleFemalePrefs[i];
            int[] maleIDogPrefs = maleDogPrefs[i];
            for (int j = 0; j < maleIFemalePrefs.length; j++) {
                maleFemaleBuilder.append(maleIFemalePrefs[j]);
                if (j < maleIFemalePrefs.length - 1) {
                    maleFemaleBuilder.append(',');
                }

                maleDogBuilder.append(maleIDogPrefs[j]);
                if (j < maleIDogPrefs.length - 1) {
                    maleDogBuilder.append(',');
                }
            }

            // Add Dog->Female/Male preferences
            int[] dogIFemalePrefs = dogFemalePrefs[i];
            int[] dogIMalePrefs = dogMalePrefs[i];
            for (int j = 0; j < dogIFemalePrefs.length; j++) {
                dogFemaleBuilder.append(dogIFemalePrefs[j]);
                if (j < dogIFemalePrefs.length - 1) {
                    dogFemaleBuilder.append(',');
                }

                dogMaleBuilder.append(dogIMalePrefs[j]);
                if (j < dogIMalePrefs.length - 1) {
                    dogMaleBuilder.append(',');
                }
            }

            Element femaleElement = new Element(TAG_FEMALE_PREF);
            femaleElement.setAttribute(ATTR_MALES, femaleMaleBuilder.toString());
            femaleElement.setAttribute(ATTR_DOGS, femaleDogBuilder.toString());
            prefsElement.addContent(femaleElement);

            Element maleElement = new Element(TAG_MALE_PREF);
            maleElement.setAttribute(ATTR_FEMALES, maleFemaleBuilder.toString());
            maleElement.setAttribute(ATTR_DOGS, maleDogBuilder.toString());
            prefsElement.addContent(maleElement);

            Element dogElement = new Element(TAG_DOG_PREF);
            dogElement.setAttribute(ATTR_FEMALES, dogFemaleBuilder.toString());
            dogElement.setAttribute(ATTR_MALES, dogMaleBuilder.toString());
            prefsElement.addContent(dogElement);
        }

        XMLUtils.createXmlFile("CWD_Preferences_" + timeStamp + ".xml", prefsElement);
    }

    public int getFemaleMalePref(int female, int male) {
        return femaleMalePrefs[female][male];
    }

    public int getFemaleDogPref(int female, int dog) {
        return femaleDogPrefs[female][dog];
    }

    public int getMaleFemalePref(int male, int female) {
        return maleFemalePrefs[male][female];
    }

    public int getMaleDogPref(int male, int dog) {
        return maleDogPrefs[male][dog];
    }

    public int getDogFemalePref(int dog, int female) {
        return dogFemalePrefs[dog][female];
    }

    public int getDogMalePref(int dog, int male) {
        return dogMalePrefs[dog][male];
    }

    public int getIndividualsPerSpecies() {
        return individualsPerSpecies;
    }
}
