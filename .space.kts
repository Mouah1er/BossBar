/**
* JetBrains Space Automation
* This Kotlin-script file lets you automate build activities
* For more info, see https://www.jetbrains.com/help/space/automation.html
*/

job("Publish on Push") {
    container("Publish and notify", "gradle") {
        kotlinScript { api ->
            api.gradle("clean")

            val recipient = MessageRecipient.Channel(ChatChannel.FromName("CI-Publish channel"))
            var content = ChatMessage.Text("Successfully published BossBar")

            try {
                api.gradle("publish")
            } catch (ex: Exception) {
                content = ChatMessage.Text("Failure to publish BossBar")
            }

            api.space().chats.messages.sendMessage(recipient, content)
        }
    }
}
