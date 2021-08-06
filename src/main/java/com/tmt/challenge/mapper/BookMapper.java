package com.tmt.challenge.mapper;

import com.tmt.challenge.dto.BookDTO;
import com.tmt.challenge.dto.BookWithStudentDTO;
import com.tmt.challenge.model.Book;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toBookDTO(Book book);

    BookWithStudentDTO toBookWithStudentDTO(Book book);

    Book toBookEntity(BookDTO bookDTO);

    List<Book> toBookEntity(Collection<BookDTO> bookDTOS);

}
