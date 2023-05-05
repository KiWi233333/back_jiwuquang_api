package com.example.back_jiwuquang_api.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * ids集合
 *
 * @className: IdList
 * @author: Kiwi23333
 * @description: ids集合
 * @date: 2023/5/1 21:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdsList {
    List<String > ids;
}
