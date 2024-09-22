async function sendMessage() {
	const input = document.getElementById('userInput').value.trim();

	if (input === "") {
		alert("Please enter a message!");
		return;
	}

	appendMessage("you", input); // Append user message

	try {
		const response = await fetch('/api/chatbot/query', {
			method: 'POST',
			headers: {
				'Content-Type': 'text/plain'
			},
			body: input
		});

		if (!response.ok) {
			throw new Error(`HTTP error! status: ${response.status}`);
		}

		const botReply = await response.text();
		appendMessage("bot", botReply); // Append bot response

	} catch (error) {
		console.error('Error:', error);
		appendMessage("bot", "Sorry, something went wrong. Please try again later."); // Error message
	}

	document.getElementById('userInput').value = ''; // Clear input field
	scrollToBottom(); // Scroll to the bottom of the chat log
}

// Function to append messages to the chat log
function appendMessage(sender, message) {
	const chatLog = document.getElementById('chatLog');
	chatLog.innerHTML += `<p class="${sender}">${sender === 'you' ? 'You' : 'Bot'}: ${message}</p>`;
}

// Function to scroll to the bottom of the chat log
function scrollToBottom() {
	const chatLog = document.getElementById('chatLog');
	chatLog.scrollTop = chatLog.scrollHeight;
}

// Ensure sendMessage is called when the button is clicked
document.getElementById('sendButton').onclick = sendMessage;

// Optional: Call sendMessage when Enter key is pressed
document.getElementById('userInput').addEventListener('keypress', function(event) {
	if (event.key === 'Enter') {
		sendMessage();
	}
});