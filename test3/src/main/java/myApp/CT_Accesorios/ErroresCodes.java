package myApp.CT_Accesorios;

public enum ErroresCodes implements ErrorCode {
	
	CTX1_CAT_SERVICE(000), // MTSREPOS ->
	
	FOLSERVICEIMPL_101(101), // MTSREPOS -> DocRepositoryImpl -> registrarDocumentos ->
	FOLSERVICEIMPL_102(102), // MTSREPOS -> 
	FOLSERVICEIMPL_103(103), // MTSREPOS -> 
	FOLSERVICEIMPL_104(104), // MTSREPOS -> 
	FOLSERVICEIMPL_105(105), // MTSREPOS ->
	FOLSERVICEIMPL_106(106), // MTSREPOS ->
	FOLSERVICEIMPL_107(107), // MTSREPOS ->
	FOLSERVICEIMPL_108(108), // MTSREPOS ->
	FOLSERVICEIMPL_109(109), // MTSREPOS ->
	FOLSERVICEIMPL_110(110), // MTSREPOS ->
	FOLSERVICEIMPL_111(111), // MTSREPOS ->
	FOLSERVICEIMPL_112(112), // MTSREPOS ->
	FOLSERVICEIMPL_113(113), // MTSREPOS ->
	FOLSERVICEIMPL_114(114), // MTSREPOS ->
	FOLSERVICEIMPL_115(115), // MTSREPOS ->
	FOLSERVICEIMPL_116(116), // MTSREPOS ->
	FOLSERVICEIMPL_117(117), // MTSREPOS ->
	FOLSERVICEIMPL_118(118), // MTSREPOS ->
	FOLSERVICEIMPL_119(119), // MTSREPOS ->
	FOLSERVICEIMPL_120(120), // MTSREPOS ->
	FOLSERVICEIMPL_121(121), // MTSREPOS ->
	FOLSERVICEIMPL_122(122), // MTSREPOS ->
	FOLSERVICEIMPL_123(123), // MTSREPOS ->
	FOLSERVICEIMPL_124(124), // MTSREPOS ->
	FOLSERVICEIMPL_125(125), // MTSREPOS ->
	FOLSERVICEIMPL_126(126), // MTSREPOS ->
	FOLSERVICEIMPL_127(127), // MTSREPOS ->
	FOLSERVICEIMPL_128(128), // MTSREPOS ->
	FOLSERVICEIMPL_129(129), // MTSREPOS ->
	FOLSERVICEIMPL_130(130), // MTSREPOS ->
	FOLSERVICEIMPL_131(131), // MTSREPOS ->
	
	

	
	;

	private final int number;

	private ErroresCodes(int number) {
		    this.number = number;
		  }

	@Override
	public int getNumber() {
		return number;
	}

}