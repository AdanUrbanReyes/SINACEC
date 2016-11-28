<?php
	class Session{
		static function init(){
			@session_start();
		}
		static function destroy(){
			session_destroy();
		}
		static function getValue($index){
			return $_SESSION[$index];
		}
		static function setValue($index,$value){
			$_SESSION[$index]=$value;
		}
		static function issetValue($index){
			return (isset($_SESSION[$index])) ? true : false;
		}
		static function removeValue($index){
			if(isset($_SESSION[$index])){
				unset($_SESSION[$index]);
			}
		}
		static function exists(){
			return (sizeof($_SESSION) > 0) ? true : false;
		}
	}
?>
