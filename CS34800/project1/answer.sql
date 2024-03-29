/* Query 1 */
SELECT T.TRAIN_NAME, S1.CITY, S2.CITY
	FROM TRAINS AS T, STATIONS AS S1, STATIONS AS S2 
	WHERE T.START_STN_ID = S1.STN_ID 
		AND T.DEST_STN_ID = S2.STN_ID;

/* Query 2 */
SELECT TRAIN_NAME AS BUSINESS
	FROM TRAINS
	WHERE BUSINESS_CLASS_FARE >= ALL ( SELECT BUSINESS_CLASS_FARE
						FROM TRAINS )
	  OR BUSINESS_CLASS_FARE <= ALL ( SELECT BUSINESS_CLASS_FARE
						FROM TRAINS )
	ORDER BY BUSINESS_CLASS_FARE DESC;
SELECT TRAIN_NAME AS ECONOMY
	FROM TRAINS
	WHERE ECONOMY_CLASS_FARE >= ALL ( SELECT ECONOMY_CLASS_FARE
						FROM TRAINS )
		OR ECONOMY_CLASS_FARE <= ALL ( SELECT ECONOMY_CLASS_FARE
						FROM TRAINS )
	ORDER BY ECONOMY_CLASS_FARE DESC;

/* Query 3 */
SELECT TRAIN_NAME, BUSINESS_CLASS_FARE, ECONOMY_CLASS_FARE, BUSINESS_CLASS_FARE - ECONOMY_CLASS_FARE AS DIFFERENCE_IN_FARES
	FROM TRAINS;

/* Query 4 */
SELECT COUNT(T.TRAIN_NAME)
	FROM TRAINS AS T, STATIONS AS S
	WHERE T.START_STN_ID = S.STN_ID 
		AND S.CITY = "INDIANAPOLIS";

/* Query 5 */
SELECT T.TRAIN_NAME, S2.CITY
	FROM TRAINS AS T, STATIONS AS S1, STATIONS AS S2
	WHERE T.START_STN_ID = S1.STN_ID 
		AND S1.CITY = "INDIANAPOLIS"
		AND T.DEST_STN_ID = S2.STN_ID;

/* Query 6 */
SELECT T.TRAIN_NAME, T.BUSINESS_CLASS_FARE, T.ECONOMY_CLASS_FARE
        FROM TRAINS AS T, STATIONS AS S1, STATIONS AS S2
        WHERE T.START_STN_ID = S1.STN_ID
		AND T.DEST_STN_ID = S2.STN_ID
		AND ( ( S1.CITY = "ATLANTA"
				AND S2.CITY = "SACRAMENTO" ) 
			OR ( S1.CITY = "SACRAMENTO"
				AND S2.CITY = "ATLANTA" ) );

/* Query 7 */
SELECT T.TRAIN_NAME, T.RUNNING_HRS, T.DIST_MILES * 1.6 AS DIST_IN_KMS
	FROM TRAINS AS T
  WHERE T.RUNNING_HRS <= ALL ( SELECT X.RUNNING_HRS
            FROM TRAINS AS X );

/* Query 8 */
SELECT DISTINCT S.CITY
	FROM STATIONS AS S, TRAIN_TIMINGS AS C1, TRAIN_TIMINGS AS C2
	WHERE S.STN_ID = C1.STN_ID
		AND S.STN_ID = C2.STN_ID
		AND C1.TRAIN_ID != C2.TRAIN_ID;

/* Query 9 */
SELECT DISTINCT T.TRAIN_NAME
	FROM TRAINS AS T, TRAIN_TIMINGS AS C
	WHERE T.TRAIN_ID = C.TRAIN_ID
		AND C.DAY_NO = ALL( SELECT X.DAY_NO
					FROM TRAIN_TIMINGS AS X
					WHERE X.TRAIN_ID = C.TRAIN_ID);

/* Query 10 */
SELECT C.SL_NO, S.CITY, C.ARRIVES_AT, C.DEPARTS_AT
	FROM TRAIN_TIMINGS AS C, STATIONS AS S
	WHERE C.TRAIN_ID = 2 
		AND C.STN_ID = S.STN_ID
	ORDER BY C.SL_NO ASC;

/* Query 11 */
SELECT T.TRAIN_ID, T.TRAIN_NAME, S1.CITY, S2.CITY, S1.STATE
	FROM TRAINS AS T, STATIONS AS S1, STATIONS S2
	WHERE T.START_STN_ID = S1.STN_ID
		AND T.DEST_STN_ID = S2.STN_ID
		AND S1.STATE = S2.STATE;
