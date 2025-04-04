package com.ll.playon.domain.image.repository;

import com.ll.playon.domain.image.entity.Image;
import com.ll.playon.domain.image.type.ImageType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByImageTypeAndReferenceId(ImageType imageType, long referenceId);

    long deleteByImageTypeAndReferenceId(ImageType imageType, long referenceId);

    @Modifying
    @Query("""
            DELETE FROM Image i
            WHERE i.referenceId = :referenceId
            And i.imageType = :imageType
            AND i.imageUrl IN :imageUrls
            """
    )
    long deleteByReferenceIdAndImageUrl(
            @Param("imageType") ImageType imageType,
            @Param("referenceId") long referenceId,
            @Param("imageUrls") List<String> imageUrls);

    @Modifying
    @Query("""
            DELETE FROM Image i
            WHERE i.referenceId = :referenceId
            And i.imageType = :imageType
            AND i.imageUrl = :imageUrl
            """
    )
    long deleteByReferenceIdAndImageUrl(
            @Param("imageType") ImageType imageType,
            @Param("referenceId") long referenceId,
            @Param("imageUrl") String imageUrl);
}
