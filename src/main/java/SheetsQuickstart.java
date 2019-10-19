import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SheetsQuickstart {
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static void loadParams(double unambiguity, double singularity, double unsubjectivity, double readability, double completeness) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1VaZILvZs4XwfeLjU1He65Buj8-vsf0FXACd__8zRlVk";
        final String range = "B1:B6";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange valueRange = new ValueRange();
        valueRange.setValues(Arrays.asList(
                Arrays.asList((Object) "Quality %"),
                Arrays.asList((Object) unambiguity),
                Arrays.asList((Object) singularity),
                Arrays.asList((Object) unsubjectivity),
                Arrays.asList((Object) readability),
                Arrays.asList((Object) completeness)));

        try {
            service.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFirstTable(ArrayList<Requirement> requirements) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1J7drbSPD6DhXadNkk2UDMYsVd-PUt74gVtSfxMqj2tc";
        final String range = "A2:D30";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        List<List<Object>> list = new ArrayList<>();

        for (Requirement requirement : requirements) {
            List<Object> row = new ArrayList<>();
            row.add(requirement.getId());
            row.add(requirement.getAmbigueTerms());
            row.add(requirement.getConnectiveTerms());
            row.add(requirement.getSubjectivityTerms());
            list.add(row);
        }

        ValueRange valueRange = new ValueRange();
        valueRange.setValues(list);

        try {
            service.spreadsheets().values().update(spreadsheetId, range, valueRange)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public static void main(String... args) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1VaZILvZs4XwfeLjU1He65Buj8-vsf0FXACd__8zRlVk";
        final String range = "B1:B6";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        Object a1 = new Object();
        a1 = "Values";
        Object b1 = new Object();
        a1 = "";
        b1 = "56";

        ValueRange valueRange = new ValueRange();
        valueRange.setValues(Arrays.asList(
                Arrays.asList(a1),
                Arrays.asList(b1),
                Arrays.asList(b1),
                Arrays.asList(b1),
                Arrays.asList(b1),
                Arrays.asList(b1)));

        service.spreadsheets().values().update(spreadsheetId, range, valueRange)
                .setValueInputOption("RAW")
                .execute();
    }
}