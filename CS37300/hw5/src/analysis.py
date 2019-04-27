"""
Part 4 Analysis

Name: Youngsik Yoon
Email: yoon161@purdue.edu
Due date: April 1, 2019
"""

import matplotlib.pyplot as plt

# Question 1 c
plt.plot([3, 6, 9, 12, 24], [1044993240.83, 383106528.274, 231696631.532, 172963635.275, 101385802.834])

plt.title("3.1 c) Within-Cluster Sum of Squares (WC-SSE) as a Function of K")
plt.ylabel("Within-Cluster Sum of Square")
plt.xlabel("K value")

plt.savefig("3_1c_k_vs_wc.png")
plt.clf()

# Question 1 e
plt.plot(35.028835732,-110.29015953, 'o', color='black')
plt.plot(35.0805969743,-111.897846821, 'o', color='black')
plt.plot(35.4387661524,-106.345388728, 'o', color='black')
plt.plot(34.9661438461,-113.775762211, 'o', color='black')
plt.plot(37.6192046595,-97.9204271573, 'o', color='black')
plt.plot(36.1122371175,-115.17362, 'o', color='black')
plt.plot(55.365130095,-2.21937883437, 'o', color='black')
plt.plot(35.0968263299,-113.452779995, 'o', color='black')
plt.plot(36.0480384973,-103.562468277, 'o', color='black')
plt.plot(35.1081066463,-113.108818464, 'o', color='black')
plt.plot(35.2191773827,-108.226260014, 'o', color='black')
plt.plot(35.4169039537,-114.329208011, 'o', color='black')

plt.title("3.1 e) 'Latitude' vs 'Longitude'")
plt.ylabel("Longitude")
plt.xlabel("Latitude")

plt.savefig("3_1e_lat_vs_long.png")
plt.clf()

plt.plot(159.473756906,601.622928177, 'o', color='black')
plt.plot(231.876681614,903.192825112, 'o', color='black')
plt.plot(65.6233000907,221.25294651, 'o', color='black')
plt.plot(673.042253521,3109.3943662, 'o', color='black')
plt.plot(12.48402746,23.5575286041, 'o', color='black')
plt.plot(3859.0,11079.5, 'o', color='black')
plt.plot(18.0715789474,21.1515789474, 'o', color='black')
plt.plot(481.902985075,2030.18656716, 'o', color='black')
plt.plot(37.0572121212,102.763393939, 'o', color='black')
plt.plot(325.24796748,1382.0, 'o', color='black')
plt.plot(103.953263497,380.883158743, 'o', color='black')
plt.plot(1556.73684211,5583.21052632, 'o', color='black')

plt.title("3.1 e) 'reviewCount' vs 'checkins'")
plt.ylabel("checkins")
plt.xlabel("reviewCount")

plt.savefig("3_1e_rc_vs_check.png")
plt.clf()

# Question 2 c
plt.plot([3, 6, 9, 12, 24], [5753674.00907, 365933.445973, 224735.438889, 219652.787042, 67750.9935819])

plt.title("3.2 c) WC-SSE as a Function of K with a Log Transform of 'reviewCount', 'checkins'")
plt.ylabel("Within-Cluster Sum of Square")
plt.xlabel("K value")

plt.savefig("3_2c_k_vs_wc.png")
plt.clf()

# Question 2 e
plt.plot(33.4810669379,-112.010318627, 'o', color='black')
plt.plot(33.4811206944,-111.991531745, 'o', color='black')
plt.plot(55.3841710565,-2.25114536775, 'o', color='black')
plt.plot(33.5209538137,-112.021172197, 'o', color='black')
plt.plot(40.7867936999,-79.9251841821, 'o', color='black')
plt.plot(36.1221254504,-115.172898286, 'o', color='black')

plt.title("3.2 e) 'Latitude' vs 'Longitude'")
plt.ylabel("Longitude")
plt.xlabel("Latitude")

plt.savefig("3_2e_lat_vs_long.png")
plt.clf()

plt.plot(1.91875931434,2.65631678914, 'o', color='black')
plt.plot(3.14034231211,4.39438236395, 'o', color='black')
plt.plot(2.62625605843,2.72181418993, 'o', color='black')
plt.plot(4.67954948266,6.03356821866, 'o', color='black')
plt.plot(2.72819338962,3.34788956096, 'o', color='black')
plt.plot(3.28226612886,4.51475021576, 'o', color='black')

plt.title("3.2 e) 'reviewCount' vs 'checkins'")
plt.ylabel("checkins")
plt.xlabel("reviewCount")

plt.savefig("3_2e_rc_vs_check.png")
plt.clf()

# Question 3 c
plt.plot([3, 6, 9, 12, 24], [37955.5372718, 23636.5418362, 9570.85932167, 7482.19741207, 3403.25904894])

plt.title("3.3 c) WC-SSE as a Function of K with Standardized Four Attributes for Clustering")
plt.ylabel("Within-Cluster Sum of Square")
plt.xlabel("K value")

plt.savefig("3_3c_k_vs_wc.png")
plt.clf()

# Question 3 e
plt.plot(-0.733471947435,-0.560806134295, 'o', color='black')
plt.plot(-0.490941317424,-0.535588855281, 'o', color='black')
plt.plot(-0.425794272248,0.625891753615, 'o', color='black')
plt.plot(-0.425833901055,-0.616898040179, 'o', color='black')
plt.plot(1.02767652274,0.676642918682, 'o', color='black')
plt.plot(-0.285835881443,-0.674945925283, 'o', color='black')
plt.plot(3.22172553697,3.61764650435, 'o', color='black')
plt.plot(-0.420190206672,-0.590922866288, 'o', color='black')
plt.plot(-0.256026510107,-0.68154589772, 'o', color='black')

plt.title("3.3 e) 'Latitude' vs 'Longitude'")
plt.ylabel("Longitude")
plt.xlabel("Latitude")

plt.savefig("3_3e_lat_vs_long.png")
plt.clf()

plt.plot(-0.207405845906,-0.214807267085, 'o', color='black')
plt.plot(0.745304518672,0.882025635524, 'o', color='black')
plt.plot(-0.202536561767,-0.212691907283, 'o', color='black')
plt.plot(6.08825713582,6.71411237606, 'o', color='black')
plt.plot(-0.209477161038,-0.305888667855, 'o', color='black')
plt.plot(20.5591061562,16.1874708566, 'o', color='black')
plt.plot(-0.251541111577,-0.352950876593, 'o', color='black')
plt.plot(2.42596724303,2.91049848216, 'o', color='black')
plt.plot(-0.215383385461,-0.193327642166, 'o', color='black')

plt.title("3.3 e) 'reviewCount' vs 'checkins'")
plt.ylabel("checkins")
plt.xlabel("reviewCount")

plt.savefig("3_3e_rc_vs_check.png")
plt.clf()

# Question 4 c
plt.plot([3, 6, 9, 12, 24], [1686704151.58, 647739234.71, 524440924.015, 306636854.87, 188387702.834])

plt.title("3.4 c) WC-SSE as a Function of K with Manhattan Distance for Clustering")
plt.ylabel("Within-Cluster Sum of Square")
plt.xlabel("K value")

plt.savefig("3_4c_k_vs_wc.png")
plt.clf()

# Question 4 e
plt.plot(34.4654141227,-113.177595599, 'o', color='black')
plt.plot(34.9911885323,-112.007747337, 'o', color='black')
plt.plot(35.2310815866,-108.004917184, 'o', color='black')
plt.plot(34.9667269632,-113.775484373, 'o', color='black')
plt.plot(41.3078241475,-79.5539315109, 'o', color='black')
plt.plot(36.1122371175,-115.17362, 'o', color='black')
plt.plot(55.3742170614,-2.23454271638, 'o', color='black')
plt.plot(35.0929989616,-113.438621808, 'o', color='black')
plt.plot(35.7857327293,-104.500346971, 'o', color='black')
plt.plot(35.1383209309,-113.058727428, 'o', color='black')
plt.plot(35.0529254356,-109.362904923, 'o', color='black')
plt.plot(35.4169039537,-114.329208011, 'o', color='black')

plt.title("3.4 e) 'Latitude' vs 'Longitude'")
plt.ylabel("Longitude")
plt.xlabel("Latitude")

plt.savefig("3_4e_lat_vs_long.png")
plt.clf()

plt.plot(14.4105420459,35.754109496, 'o', color='black')
plt.plot(222.203358209,864.677238806, 'o', color='black')
plt.plot(88.6955810147,308.791052919, 'o', color='black')
plt.plot(684.225352113,3104.25352113, 'o', color='black')
plt.plot(14.9408230153,26.707028405, 'o', color='black')
plt.plot(3859.0,11079.5, 'o', color='black')
plt.plot(18.6704663212,22.8632124352, 'o', color='black')
plt.plot(471.305343511,2041.5648855, 'o', color='black')
plt.plot(49.6832280299,149.610568639, 'o', color='black')
plt.plot(332.773076923,1372.34615385, 'o', color='black')
plt.plot(142.763073639,540.337246531, 'o', color='black')
plt.plot(1556.73684211,5583.21052632, 'o', color='black')

plt.title("3.4 e) 'reviewCount' vs 'checkins'")
plt.ylabel("checkins")
plt.xlabel("reviewCount")

plt.savefig("3_4e_rc_vs_check.png")
plt.clf()

# Question 5 c
plt.plot([3, 6, 9, 12, 24], [63821374.1542, 17553725.2942, 16163331.7385, 11604621.9134, 5198801.36741])

plt.title("3.5 c) WC-SSE as a Function of K with 6% Random Sample of Data")
plt.ylabel("Within-Cluster Sum of Square")
plt.xlabel("K value")

plt.savefig("3_5c_k_vs_wc.png")
plt.clf()

# Question 5 e
plt.plot(35.5893210673,-114.55414964, 'o', color='black')
plt.plot(36.2985641527,-103.355000942, 'o', color='black')
plt.plot(39.1818895507,-89.0041923208, 'o', color='black')
plt.plot(35.7981370927,-112.529972901, 'o', color='black')
plt.plot(34.8930813393,-110.125496484, 'o', color='black')
plt.plot(35.2233023958,-108.078498869, 'o', color='black')


plt.title("3.5 e) 'Latitude' vs 'Longitude'")
plt.ylabel("Longitude")
plt.xlabel("Latitude")

plt.savefig("3_5e_lat_vs_long.png")
plt.clf()

plt.plot(608.866666667,2624.73333333, 'o', color='black')
plt.plot(46.1556420233,136.568093385, 'o', color='black')
plt.plot(14.55,27.0448717949, 'o', color='black')
plt.plot(262.787878788,1069.78787879, 'o', color='black')
plt.plot(185.172413793,621.25862069, 'o', color='black')
plt.plot(88.2131147541,319.56557377, 'o', color='black')

plt.title("3.5 e) 'reviewCount' vs 'checkins'")
plt.ylabel("checkins")
plt.xlabel("reviewCount")

plt.savefig("3_5e_rc_vs_check.png")
plt.clf()