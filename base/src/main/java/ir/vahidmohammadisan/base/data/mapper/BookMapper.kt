package ir.vahidmohammadisan.base.data.mapper

import ir.vahidmohammadisan.base.domain.model.Book
import ir.vahidmohammadisan.base.domain.model.BookDetails
import org.jsoup.Jsoup

fun String.toBooks(): List<Book> {
    val document = Jsoup.parse(this)
    val rows = document.select("tr[valign=top]")
    val books = mutableListOf<Book>()

    for (row in rows) {
        val columns = row.select("td")

        if (columns.size < 10) continue

        val id = columns[0].text().toIntOrNull() ?: continue
        val titleElement = columns[2].select("a[id]").first()
        val title = titleElement?.text() ?: ""
        val md5 = titleElement?.attr("href")?.substringAfter("md5=") ?: ""
        val cover = "https://libgen.is/covers/${id}/${md5}.jpg"
        val publisher = columns[3].text()
        val date = columns[4].text().toIntOrNull() ?: 0
        val page = columns[5].text().toIntOrNull() ?: 0
        val language = columns[6].text()
        val fileSize = columns[7].text().removeSuffix(" Mb").toIntOrNull() ?: 0
        val type = columns[8].text()

        books.add(
            Book(
                id = id,
                title = title,
                md5 = md5,
                cover = cover,
                page = page,
                date = date,
                language = language,
                type = type,
                fileSize = fileSize,
                publisher = publisher,
            )
        )
    }

    return books
}


fun String.toBookDetails(): BookDetails {
    val document = Jsoup.parse(this)

    val pdfDownloadLink = document.select("#download h2 a").firstOrNull()?.attr("href") ?: ""
    val descriptionElement = document.select("#info p:contains(Publisher:)").firstOrNull()
    val description = descriptionElement?.text() ?: ""
    val author = "Unknown"
    val isbn = document.select("#info p:contains(ISBN:)").text().substringAfter("ISBN:").trim()
    val filename =
        document.select("h1").firstOrNull()?.text()?.replace(".", "") ?: pdfDownloadLink.split("/")
            .last().replace(".pdf", "")

    return BookDetails(
        downloadLink = pdfDownloadLink,
        description = description,
        fileName = filename,
        author = author,
        isbn = isbn
    )
}
