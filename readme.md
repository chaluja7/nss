# Instalační návod #


## Databáze PostgreSQL ##
* Stáhněte a nainstalujte si databázi PostgreSQL (aplikace testována s verzí 9.3, nicméně mírně odlišná verze vadit nebude).
 * Odkaz na stažení: http://www.postgresql.org/download/
 * Instalační manuál: http://postgres.cz/wiki/Instalace_PostgreSQL
 * Defaultně bude vytvořen user "postgres" s heslem "postgres". Samozřejmě můžeme vytvořit libovolného usera.
* Stáhněte a nainstalujte si aplikaci pgAdmin (volitelné).
 * Odkaz na stažení: http://www.pgadmin.org/download/
* V aplikaci pgAdmin (nebo dle Vašich zvyklostí) vytvořte na svém lokálním databázovém (PostgreSQL) serveru databázi "nss".

    ``CREATE DATABASE nss WITH OWNER = postgres ENCODING = 'UTF8' TABLESPACE = pg_default LC_COLLATE = 'cs_CZ.UTF-8' LC_CTYPE = 'cs_CZ.UTF-8' CONNECTION LIMIT = -1;``

    V případě využití jiného usera samozřejmě změníme hodnotu OWNER.

* Do databáze "nss" naimportujte schéma a data databáze z rozbaleného archivu ``/src/db/postgreDatabase.zip`` na tomto CD (nebo archivu se shodným jménem nahraným v KOSu).
    Lze provést např. volbou restore (obnovit) z aplikace pgAdmin. Více viz: http://www.pgadmin.org/docs/1.16/restore.html
    **Import může trvat až několik desítek minut. Importovat se bude cca 1 000 000 záznamů.**


## Databáze Neo4j ##
* Stáhněte a nainstalujte si databázi Neo4j (MUSÍ být verze 2.1.x, nejlépe 2.1.7, testováno i s 2.1.8. Verze 2.2 a vyšší nebude fungovat).
 * Odkaz na stažení: http://neo4j.com/download/other-releases/
 * Instalační manuál: http://neo4j.com/docs/stable/server-installation.html

* Do složky ``/data/`` v rootu nainstalované (rozbalené) Neo4j databáze zkopírujte celý obsah rozbaleného archivu ``/src/db/neo4jdatabase.zip`` z tohoto CD (nebo archivu neo4jDatabase.rar (part01 - part05) nahraného v KOSu).
 * Velikost databázového schématu graph.db je přes 1.7 GB. Kopírování tedy může trvat delší dobu.


## Aplikační server WildFly ##
* Stáhněte a nainstalujte si aplikační server WildFly 8 (aplikace testována pro verzi 8.1).
 * Odkaz na stažení: http://wildfly.org/downloads/
 * Instalační manuál: https://docs.jboss.org/author/display/WFLY8/Getting+Started+Guide


## Maven ##
* Stáhněte a nainstalujte si MAVEN
 * Odkaz na stažení a instalační návod: https://maven.apache.org/download.cgi


## Spuštění aplikace ##
* V libovolném IDE si vytvořte maven projekt nad tímto adresářem.
* V "resources/nssLocal.properties" si nastavte své parametry pro databázi PostgreSQL a cestu k databázi Neo4j.
* Příkazem ``mvn clean install`` nainstalujte aplikaci do lokálního repozitáře, tímto také dojde ke stažení všech maven závislostí. Současně se provede řada integračních a jednotkových testů.
 * Pokud nechcete testy spouštět, využijte za příkazem přepínač ``-DskipTests=true``
* .war aplikace nasaďte na aplikační server WildFly.
* Po spuštění serveru bude aplikace dostupná na ``localhost:8080/NSS/`` (při defaultním nastavení).

## Průchod aplikací ##
* Vyhledávání
 * V případě, že bylo vše nastaveno podle tohoto návodu, je možné ihned vyhledávat nad kompletními daty pražské MHD.
 * V databázi jsou uloženy všechny stanice pražské MHD. Po napsání 3 prvních znaků do pole "odkud" nebo "kam" se začnou stanice napovídat.
 * Databáze obsahuje data platná pouze v rozmezí 07/2015 - 08/2015. Vyhledávání tedy musí probíhat v tomto intervalu, jinak nebudou nalezeny žádné výsledky.
 * Vyhledávání proběhne po vybrání výchozí a cílové stanice, data a času výjezdu/příjezdu a dalších parametrů vyhledávacího formuláře.
 * Pokud nevyberete databázi "Neo4j" může vyhledávání trvat velmi dlouho (nemusí doběhnout nikdy).
 * Nad databází Neo4j se vyhledává pro interval šesti hodin. Nad databází PostgreSQL jen pro interval jedné hodiny.
* Administrace
 * Pokud jste správně importovali data do databáze PostgreSQL, do administrace aplikace se přihlásíte těmito údaji:
   * Login: admin
    * Heslo: a
 * Tohoto uživatele případně vytvoříte i provedením příslušného SQL příkazu z ``resources/import.sql``
* Další informace jsou dostupné po přihlášení do administrační části.
