import java.io.IOException;
import java.sql.*;
import java.util.List;

public class IndlaesDatafil {

	public static void main(String[] args) {

		String host = "localhost";
		String port = "3306";
		String db = "tidsmaskinen";

		String username = "root";
		String password = "X0aMu0";

		String url = "jdbc:mysql://" + host + ":" + port + "/" + db;

		IndlaesPersonerOgTilmeldinger laeser = new IndlaesPersonerOgTilmeldinger();
		try {
			Connection connection = DriverManager.getConnection(url, username, password);

			List<PersonOgTilmelding> personerOgTilmeldinger = laeser.indlaesPersonerOgTilmeldinger("tilmeldinger.csv");
			for (int i = 0; i < personerOgTilmeldinger.size(); i++) {
				PersonOgTilmelding personOgTilmelding = personerOgTilmeldinger.get(i);
				if (personOgTilmelding.getTilmelding() != null)
					try {
						String personSQL =
								"INSERT INTO person(email, for_navn, efter_navn, koen, foelselsdato) VALUES (?,?,?,?,?);";
						PreparedStatement statement1 = connection.prepareStatement(personSQL);
						statement1.setString(1, personOgTilmelding.getPerson().getEmail());
						statement1.setString(2, personOgTilmelding.getPerson().getFornavn());
						statement1.setString(3, personOgTilmelding.getPerson().getEfternavn());
						statement1.setString(4, personOgTilmelding.getPerson().getKoen());
						statement1.setDate(5, new Date(personOgTilmelding.getPerson().getFoedselsdato().getTime()));
						statement1.executeUpdate();

						String tilmeldingSQL =
								"INSERT INTO tilmelding(Email, klub_id, eventtype_id, eventdato) VALUES (?,?,?,?)";
						PreparedStatement statement2 = connection.prepareStatement(tilmeldingSQL);
						statement2.setString(1, personOgTilmelding.getPerson().getEmail());
						statement2.setString(2, personOgTilmelding.getTilmelding().getForeningsId());
						statement2.setString(3, personOgTilmelding.getTilmelding().getEventTypeId());
						statement2.setDate(4, new Date(personOgTilmelding.getTilmelding().getEventDate().getTime()));
						statement2.executeUpdate();

						statement1.close();
						statement2.close();

					} catch (Exception e){
						e.printStackTrace();
					}
				else
					System.out.println(personOgTilmelding.getPerson().getEmail() + ": Ingen tilhørende tilmelding");
			}
			connection.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}


