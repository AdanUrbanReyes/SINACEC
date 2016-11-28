<?
	class XML{
		static function xmlToArray($xml) {
			$array = array();
			foreach ($xml as $key => $value) {
				switch(true) {
					case is_object($value):
						$array[$key] = xmlToArray($value);
					break;
					case is_array($value):
						$array[$key] = xmlToArray($value);
					break;
					default:
						$array[$key] = $value;
				}
			}
			return $array;
		}
		static function xmlToObject($xml){
			$arrayObjects = XML::xmlToArray($xml);
			return ( strcmp($arrayObjects['return'], 'null') === 0 ) ? null : json_decode($arrayObjects['return']); //NOTE: we can create a variable for save the name of return
		}
	}
?>
