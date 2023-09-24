package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items); //여기서 모델에 등록함 => view(html 템플릿)에서 모델에 등록한 객체 사용 가능
        return "basic/items"; //view name (템플릿에서 호출하는 듯)
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam Integer price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);

//        model.addAttribute("item", item); 자동 추가 -> 생략 가능
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        //클래스 명의 첫번째 글자를 소문자로 바꿔서 ModelAttribute에 추가함
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item) { //개인적으로 V3가 제일 무난
        //우리가 임의로 만든 클래스의 경우 ModelAttribute 적용됨 String 같은 건 RequestParam
        itemRepository.save(item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId(); //리다이렉션
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId()); //리다이렉트 할 때 해당 attribute 값까지 같이 보냄
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}"; //그래서 리턴값에 itemId 사용 가능
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {// 경로에서 변수 추출
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String editItem(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; //redirect를 안하면 itemId/edit 경로에 있음 -> 빠져나올 수 있게 해줌
        // 같은 경로에 있으면 새로고침 할 때 계속 Post가 됨 (왜냐하면 경로가 post: /id/edit이기 때문)
    }

    /**
     * 테스트용 데이터 추가
     * 기억이 잘 안나서 그런건지 대충 다시 확인해보니
     * postconstruct 어노테이션은 의존성 주입이 끝난 후에 바로 실행되는 어노테이션으로
     * 실행할 때 추가로 실행되었으면 좋겠다는 클래스에 붙이는 듯
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}

