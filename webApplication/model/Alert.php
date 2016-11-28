<?php
	class Alert{
		public $message;
		public $type;
		function __construct($type,$message){
			$this->message = $message;
			$this->type = $type;
		}
	}
?>
