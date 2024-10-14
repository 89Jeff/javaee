/**
 * Confirmação de exclusão de um contato
 * @author Jefferson
 * @param idcon
 */

function confirmar(idcon) {
	let resposta = confirm("Confirma a exclusão deste contato ?")
	if(resposta === true) {
		/*teste de recebimento
		alert(idcon)*/
		window.location.href = "delete?idcon=" + idcon
	}
}